package org.irods.mydrop.controller

import grails.converters.*
import org.irods.mydrop.service.ShoppingCartService

import org.irods.jargon.core.connection.*
import org.irods.jargon.core.exception.*
import org.irods.jargon.core.pub.*
import org.irods.jargon.core.pub.domain.DataObject
import org.irods.jargon.core.pub.io.IRODSFile
import org.irods.jargon.core.utils.LocalFileUtils
import org.irods.jargon.usertagging.FreeTaggingService
import org.irods.jargon.usertagging.IRODSTaggingService
import org.irods.jargon.usertagging.TaggingServiceFactory
import org.springframework.security.core.context.SecurityContextHolder


/**
 * Controller for browser functionality
 * @author Mike Conway - DICE (www.irods.org)
 */

class BrowseController {

	IRODSAccessObjectFactory irodsAccessObjectFactory
	TaggingServiceFactory taggingServiceFactory
	IRODSAccount irodsAccount
	ShoppingCartService shoppingCartService

	/**
	 * Interceptor grabs IRODSAccount from the SecurityContextHolder
	 */
	def beforeInterceptor = {
		def irodsAuthentication = SecurityContextHolder.getContext().authentication
		
		if (irodsAuthentication == null) {
			throw new JargonRuntimeException("no irodsAuthentication in security context!")
		}

		irodsAccount = irodsAuthentication.irodsAccount
		log.debug("retrieved account for request: ${irodsAccount}")
	}

	def afterInterceptor = {
		log.debug("closing the session")
		irodsAccessObjectFactory.closeSession()
	}

	/**
	 * Set the parent dir based on the possibility of 'strict ACL' being set
	 */
	def establishParentDir = {

		log.info("establishParentDir")

		def parent = params['dir']
		log.info "loading tree for parent path: ${parent}"

		if (!parent) {
			log.error "no parent param set"
			throw new
			JargonException("no parent param set")
		}

		if (parent != "/") {
			log.info "parent not root use as is"
		} else {
			log.info "parent set to root, see if strict acl set"
			def environmentalInfoAO = irodsAccessObjectFactory.getEnvironmentalInfoAO(irodsAccount)
			def isStrict = environmentalInfoAO.isStrictACLs()
			log.info "is strict?:{isStrict}"
			if (isStrict) {
				parent = "/" + irodsAccount.zone + "/home/" + irodsAccount.userName + "/"
			}
		}

		log.info "set root dir as: ${parent}"
		def jsonResult = ["parent" : parent]

		log.info "jsonResult:${jsonResult}"

		render jsonResult as JSON
	}

	/**
	 * Render the tree node data for the given parent.  This will use the HTML style AJAX response to depict the children using unordered lists.
	 * <p/>
	 *  Requires param 'dir' from request to derive parent
	 *
	 */
	def loadTree = {
		def parent = params['dir']
		log.info "loading tree for parent path: ${parent}"
		def collectionAndDataObjectListAndSearchAO = irodsAccessObjectFactory.getCollectionAndDataObjectListAndSearchAO(irodsAccount)
		def collectionAndDataObjectList = collectionAndDataObjectListAndSearchAO.listDataObjectsAndCollectionsUnderPath(parent)
		log.debug("retrieved collectionAndDataObjectList: ${collectionAndDataObjectList}")
		render collectionAndDataObjectList as JSON
	}

	/**
	 * Called by tree control to load directories under a given node
	 */
	def ajaxDirectoryListingUnderParent = {
		def parent = params['dir']
		log.info "ajaxDirectoryListingUnderParent path: ${parent}"

		if (!parent) {
			log.error "no dir param set"
			throw new
			JargonException("no dir param set")
		}

		if (parent != "/") {
			log.info "parent not root use as is"
		} else {
			log.info "parent set to root, see if strict acl set"
			def environmentalInfoAO = irodsAccessObjectFactory.getEnvironmentalInfoAO(irodsAccount)
			def isStrict = environmentalInfoAO.isStrictACLs()
			log.info "is strict?:{isStrict}"
			if (isStrict) {
				parent = "/" + irodsAccount.zone + "/home/" + irodsAccount.userName
			}
		}

		def collectionAndDataObjectListAndSearchAO = irodsAccessObjectFactory.getCollectionAndDataObjectListAndSearchAO(irodsAccount)
		def collectionAndDataObjectList = collectionAndDataObjectListAndSearchAO.listDataObjectsAndCollectionsUnderPath(parent)
		log.debug("retrieved collectionAndDataObjectList: ${collectionAndDataObjectList}")

		def jsonBuff = []
		//jsonBuff.add(['parent':parent])

		collectionAndDataObjectList.each {

			def icon
			def state
			def type
			if (it.isDataObject()) {
				icon = "../images/file.png"
				state = "open"
				type = "file"
			} else {
				icon = "folder"
				state = "closed"
				type = "folder"
			}

			def attrBuf = ["id":it.formattedAbsolutePath, "rel":type]

			jsonBuff.add(
					["data": it.nodeLabelDisplayValue,"attr":attrBuf, "state":state,"icon":icon, "type":type]
					)
		}

		render jsonBuff as JSON
	}

	def displayBrowseGridDetails = {
		def absPath = params['absPath']
		if (absPath == null) {
			throw new JargonException("no absolute path passed to the method")
		}

		log.info "displayBrowseGridDetails for absPath: ${absPath}"
		CollectionAndDataObjectListAndSearchAO collectionAndDataObjectListAndSearchAO = irodsAccessObjectFactory.getCollectionAndDataObjectListAndSearchAO(irodsAccount)
		def retObj = collectionAndDataObjectListAndSearchAO.getFullObjectForType(absPath);
		def isDataObject = retObj instanceof DataObject

		// if data object, show the info details instead...
		if (isDataObject) {
			redirect(action:"fileInfo", params:[absPath:absPath])
			return
		}

		def entries = collectionAndDataObjectListAndSearchAO.listDataObjectsAndCollectionsUnderPath(absPath)
		log.debug("retrieved collectionAndDataObjectList: ${entries}")
		render(view:"browseDetails", model:[collection:entries, parent:retObj, showLite:collectionAndDataObjectListAndSearchAO.getIRODSServerProperties().isTheIrodsServerAtLeastAtTheGivenReleaseVersion("rods3.0")])

	}

	def displayPulldownDataDetails = {
		def absPath = params['absPath']
		if (absPath == null) {
			throw new JargonException("no absolute path passed to the method")
		}

		log.info "displayPulldownDataDetails for absPath: ${absPath}"
		render(view:"pulldownDataDetails")

	}


	/**
	 * Build data for the 'large' file info display
	 */
	def fileInfo = {
		def absPath = params['absPath']
		if (absPath == null) {
			throw new JargonException("no absolute path passed to the method")
		}

		log.info "fileInfo for absPath: ${absPath}"
		CollectionAndDataObjectListAndSearchAO collectionAndDataObjectListAndSearchAO = irodsAccessObjectFactory.getCollectionAndDataObjectListAndSearchAO(irodsAccount)
		def retObj = null
		// If I cant find any data just put a message up in the display area
		try {
			retObj = collectionAndDataObjectListAndSearchAO.getFullObjectForType(absPath)

			if (!retObj) {
				log.error "no data found for path ${absPath}"
				render(view:"noInfo")
				return
			}
		} catch (DataNotFoundException) {
			render(view:"noInfo")
			return
		}

		def isDataObject = retObj instanceof DataObject
		def getThumbnail = false

		log.info "is this a data object? ${isDataObject}"

		FreeTaggingService freeTaggingService = taggingServiceFactory.instanceFreeTaggingService(irodsAccount)
		IRODSTaggingService irodsTaggingService = taggingServiceFactory.instanceIrodsTaggingService(irodsAccount)
		if (isDataObject) {
			String extension = LocalFileUtils.getFileExtension(retObj.dataName).toUpperCase()
			log.info("extension is:${extension}")

			if (extension == ".JPG" || extension == ".GIF" || extension == ".PNG" || extension == ".TIFF" || extension == ".TIF") {
				getThumbnail = true;
			}

			log.info("getting free tags for data object")
			def freeTags = freeTaggingService.getTagsForDataObjectInFreeTagForm(absPath)
			log.info("rendering as data object: ${retObj}")
			def commentTag = irodsTaggingService.getDescriptionOnDataObjectForLoggedInUser(absPath)

			def comment = ""
			if (commentTag) {
				comment = commentTag.getTagData()
			}

			render(view:"dataObjectInfo", model:[dataObject:retObj,tags:freeTags,comment:comment,getThumbnail:getThumbnail, isDataObject:isDataObject,showLite:collectionAndDataObjectListAndSearchAO.getIRODSServerProperties().isTheIrodsServerAtLeastAtTheGivenReleaseVersion("rods3.0")])
		} else {
			log.info("getting free tags for collection")
			def freeTags = freeTaggingService.getTagsForCollectionInFreeTagForm(absPath)
			def commentTag = irodsTaggingService.getDescriptionOnCollectionForLoggedInUser(absPath)

			def comment = ""
			if (commentTag) {
				comment = commentTag.getTagData()
			}
			log.info("rendering as collection: ${retObj}")
			render(view:"collectionInfo", model:[collection:retObj,comment:comment,tags:freeTags,  isDataObject:isDataObject, showLite:collectionAndDataObjectListAndSearchAO.getIRODSServerProperties().isTheIrodsServerAtLeastAtTheGivenReleaseVersion("rods3.0")])
		}
	}

	/**
	 * Build data for the 'mini' file info display
	 */
	def miniInfo = {
		def absPath = params['absPath']
		if (absPath == null) {
			throw new JargonException("no absolute path passed to the method")
		}

		log.info "mini for absPath: ${absPath}"
		CollectionAndDataObjectListAndSearchAO collectionAndDataObjectListAndSearchAO = irodsAccessObjectFactory.getCollectionAndDataObjectListAndSearchAO(irodsAccount)

		def retObj = collectionAndDataObjectListAndSearchAO.getFullObjectForType(absPath)

		def isDataObject = retObj instanceof DataObject

		def getThumbnail = false

		log.info "is this a data object? ${isDataObject}"

		FreeTaggingService freeTaggingService = taggingServiceFactory.instanceFreeTaggingService(irodsAccount)
		IRODSTaggingService irodsTaggingService = taggingServiceFactory.instanceIrodsTaggingService(irodsAccount)
		if (isDataObject) {
			log.info("getting free tags for data object")
			def freeTags = freeTaggingService.getTagsForDataObjectInFreeTagForm(absPath)
			def commentTag = irodsTaggingService.getDescriptionOnDataObjectForLoggedInUser(absPath)

			def comment = ""
			if (commentTag) {
				comment = commentTag.getTagData()
			}

			log.info("rendering as data object: ${retObj}")

			String extension = LocalFileUtils.getFileExtension(retObj.dataName).toUpperCase()
			log.info("extension is:${extension}")

			if (extension == ".JPG" || extension == ".GIF" || extension == ".PNG" || extension == ".TIFF" ||   extension == ".TIF") {
				getThumbnail = true;
			}

			render(view:"miniInfoDataObject", model:[dataObject:retObj,tags:freeTags,comment:comment,getThumbnail:getThumbnail])
		} else {
			log.info("getting free tags for collection")
			def freeTags = freeTaggingService.getTagsForCollectionInFreeTagForm(absPath)

			def commentTag = irodsTaggingService.getDescriptionOnCollectionForLoggedInUser(absPath)

			def comment = ""
			if (commentTag) {
				comment = commentTag.getTagData()
			}

			log.info("comment was:${comment}")

			log.info("rendering as collection: ${retObj}")
			render(view:"miniInfoCollection", model:[collection:retObj,comment:comment,tags:freeTags])
		}
	}

	/**
	 * build the rename dialog
	 */
	def prepareRenameDialog = {
		log.info("prepareRenameDialog()")

		def absPath = params['absPath']
		if (absPath == null) {
			log.error "no absPath in request"
			def message = message(code:"error.no.path.provided")
			response.sendError(500,message)
		}

		log.info("abs path:${absPath}")

		/*
		 * Get the last part of the path from the given absolute path
		 */

		IRODSFile targetFile = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount).instanceIRODSFile(absPath)

		log.info("target file obtained")
		if (!targetFile.exists()) {
			log.error "absPath does not exist in iRODS"
			def message = message(code:"error.no.data.found")
			response.sendError(500,message)
		}

		log.info("target file exists")

		String fileName = targetFile.name
		render(view:"renameDialog", model:[fileName:fileName, absPath:absPath])
	}

 
	/**
	 * Prepare the 'new folder' dialog
	 */
	def prepareNewFolderDialog = {
		log.info("prepareNewFolderDialog()")

		def absPath = params['absPath']
		if (absPath == null) {
			log.error "no absPath in request"
			def message = message(code:"error.no.path.provided")
			response.sendError(500,message)
		}

		log.info("abs path:${absPath}")

		/*
		 * If this is a data object, get the parent
		 */

		String parentPath = null;

		IRODSFile targetFile = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount).instanceIRODSFile(absPath)

		log.info("target file obtained")
		if (!targetFile.exists()) {
			log.error "absPath does not exist in iRODS"
			def message = message(code:"error.no.data.found")
			response.sendError(500,message)
		}

		if (targetFile.isFile()) {
			log.info("is a file, use the parent collection as the parent of the new folder")
			parentPath = targetFile.getParent()
		} else {
			log.info("is a collection use abs path as parent of new folder")
			parentPath = targetFile.getAbsolutePath()
		}

		log.info("parent path:${parentPath}")

		String fileName = targetFile.name
		render(view:"newFolderDialog", model:[absPath:absPath])
	}
	
	def addFileToCart = {
		log.info ("addFileToCart")
		String fileName = params['absPath']
		if (!fileName) {
			log.error "no file name in request"
			def message = message(code:"error.no.path.provided")
			response.sendError(500,message)
		}
		
		log.info("adding ${fileName} to the shopping cart")

		shoppingCartService.addToCart(fileName, irodsAccount)
		
		log.info("shopping cart: ${session.shoppingCart}")
		
		log.info("file added")
		render fileName
	}
	
	def listCart = {
		log.info("listCart")
		render(view:"listCart")
	}
}

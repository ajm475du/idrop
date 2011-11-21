// Place your Spring DSL code here
beans = {

	browseController(org.irods.mydrop.controller.BrowseController) {
		irodsAccessObjectFactory = ref("irodsAccessObjectFactory")
		taggingServiceFactory = ref("taggingServiceFactory")
	}
	
	tagsController(org.irods.mydrop.controller.TagsController) {
		irodsAccessObjectFactory = ref("irodsAccessObjectFactory")
		taggingServiceFactory = ref("taggingServiceFactory")
	}
	
	searchController(org.irods.mydrop.controller.SearchController) {
		irodsAccessObjectFactory = ref("irodsAccessObjectFactory")
		taggingServiceFactory = ref("taggingServiceFactory")
	}
	
	metadataController(org.irods.mydrop.controller.MetadataController) {
		irodsAccessObjectFactory = ref("irodsAccessObjectFactory")
	}
	
	sharingController(org.irods.mydrop.controller.SharingController) {
		irodsAccessObjectFactory = ref("irodsAccessObjectFactory")
	}
	
	auditController(org.irods.mydrop.controller.AuditController) {
		irodsAccessObjectFactory = ref("irodsAccessObjectFactory")
	}
	
	idropLiteController(org.irods.mydrop.controller.IdropLiteController) {
		irodsAccessObjectFactory = ref("irodsAccessObjectFactory")
	}
	
	imageController(org.irods.mydrop.controller.ImageController) {
		irodsAccessObjectFactory = ref("irodsAccessObjectFactory")
		imageServiceFactory = ref("imageServiceFactory")
	}
	
	cartController(org.irods.mydrop.controller.CartController) {
		irodsAccessObjectFactory = ref("irodsAccessObjectFactory")
	}
	
}

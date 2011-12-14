<div id="detailsToolbar" style="height:100%;overflow:visible;margin-left:auto;margin-right:auto;">
<ul id="detailsToolbarMenu" class="sf-menu">
	
	<li id="menuFile"><a href="#file"><g:message code="text.file"/></a>
	<ul>
		<li id="menuNewFolder"><a href="#newFolder" onclick="newFolderViaToolbar()"><g:message code="text.new.folder" /></a></li>
		<li id="menuRename"><a href="#rename" onclick="renameViaToolbar()"><g:message code="text.rename" /></a></li>
		<li id="menuDelete"><a href="#delete" 	onclick="deleteViaToolbar()"><g:message code="default.button.delete.label" /></a></li>
	</ul>
	</li>
	<li id="menuUploadDownload"><a href="#uploadDownload" onclick="showUploadDialog()"><g:message code="text.upload.and.download"/></a>
	<ul>
		<li id="menuUpload"><a href="#upload" onclick="showUploadDialog()"><g:message code="text.upload" /></a></li>
		<li id="menuDownload"><a href="#download" onclick="downloadAction()"><g:message code="text.download" /></a></li>
		<g:if test="${showLite}">
		<li id="menuBulkUpload"><a href="#bulkupload" onclick="showIdropLite()"><g:message code="text.bulk.upload" /></a></li>
		
		<li id="menuAddToCart"><a href="#addToCart" onclick="addToCartViaToolbar()"><g:message code="text.add.to.cart" /></a></li>
		</g:if>
		
	</ul>
	</li>
	<li id="menuSharing"><a href="#sharing"><g:message code="text.sharing"/></a>
	<ul>
		<li id="menuCreateTicket"><a href="#createTicket"><g:message code="text.create.ticket" /></a></li>
		<li id="menuShareWithUsers"><a href="#shareWithUsers"><g:message code="text.share" /></a></li>
	</ul>
	</li>

	</ul>
</div>
<script>
$(function() {
	
	$("ul.sf-menu").superfish();
});

function downloadAction() {
	downloadViaToolbar();

}

</script>
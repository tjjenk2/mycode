<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	
<style type="text/css">
	#fileTable {
		font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
		width:100%;
		border-collapse:collapse;
	}
	
	#fileTable td, #fileTable th {
		font-size:1.0em;
		border:1px solid #98bf21;
		padding:3px 7px 2px 7px;
	}
	
	#fileTable th {
		font-size:1.2em;
		text-align:left;
		padding-top:5px;
		padding-bottom:4px;
		background-color:#A7C942;
		color:#fff;
	}
	
	#fileTable tr.alt td {
		color:#000;
		background-color:#EAF2D3;
	}
	
	h1.title {
		color:#617f10;
		background-color:transparent;
		margin-top:0px;
		font-size:120%;
	}

</style>	
	
</head>
<body>
<h1 class="title">${versionInfo}</h1>

<c:choose>
	<c:when test="${not empty fileInfoList}">
		<table id="fileTable">
			<thead>
				<tr>
					<th>#</th>
					<th>File Name</th>
					<th>Last Modified</th>
					<th>File Size (bytes)</th>
					<th>rest method</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${fileInfoList}" varStatus="loop">
				 	<spring:url value="/fetch/file?fileName=${fileInfoList[loop.index].fileName}" var="currentEncodedUrl" htmlEscape="true"/>
				 	<c:choose>
				 		<c:when test="${loop.index mod 2 == 0}">
				 			<tr>
				 		</c:when>
				 		<c:otherwise>
				 			<tr class="alt">
				 		</c:otherwise>
				 	</c:choose>
				 		<td><div align="center">${loop.index}</div></td>
						<td>${fileInfoList[loop.index].fileName}</td>
						<td><div align="center">${fileInfoList[loop.index].formattedLastModified}</div></td>
						<td><div align="right">${fileInfoList[loop.index].fileSize}</div></td>
						<td><a href="${currentEncodedUrl}">${pageContext.request.contextPath}/fetch/file?fileName=${fileInfoList[loop.index].fileName}</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<h1>No files found!</h1>
	</c:otherwise>
</c:choose>
</body>
</html>

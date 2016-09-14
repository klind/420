<%@ page import="com.allegiant.util.property.Property" %>
<%@ page import="com.allegiant.util.property.PropertySet" %>
<%@ page import="com.allegiant.util.property.PropertySetUtil" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${project.artifactId}</title>
</head>
<body>
<h1>${project.artifactId}</h1>
<h2>Version: ${project.version}</h2>
<h2>Build DateTime: ${build.timestamp}</h2>
<h2>Source Commit: ${build.number}</h2>

<a href="docs/ui">Swagger REST API Docs</a>

<br/>
<h2>Web Service(s):</h2>
<ul>
    <li>${project.artifactId}</li>
</ul>
<%
    List<PropertySet> propertySets = PropertySetUtil.getPropertySets(true);
    if (propertySets != null) {
%>
<br/>

<h2>Configuration Information</h2>
<table border=”1″>
    <tr>
        <td><b>System Property Name</b></td>
        <td><b>Effective Value</b></td>
        <td><b>Default Value</b></td>
        <td><b>ENUM Value Name</b></td>
    </tr>

    <%
        for (PropertySet propertySet : propertySets) {
    %>
    <tr bgcolor="#d3d3d3">
        <td colspan="4"><h4><%= propertySet.getName() %>
        </h4></td>
    </tr>
    <%
        for (Property p : propertySet.getProperties()) {
    %>
    <tr>
        <td>
            <%= p.getSystemPropertyName() %>
        </td>
        <td <%= p.getIsValueDefault() ? "" : "style=\"color: blue; font-style: italic\"" %>>
            <%= p.getValue() %>
        </td>
        <td>
            <%= p.getValueDefault() %>
        </td>
        <td>
            <%= p.getEnumValue() %>
        </td>
    </tr>
    <% } %>
    <% } %>
</table>
<% } %>
</body>
</html>
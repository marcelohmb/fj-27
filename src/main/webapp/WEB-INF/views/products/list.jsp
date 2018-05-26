<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="cdc"%>

<cdc:page title="Listagem de Produtos">


<sec:authorize access="isAuthenticated()">
<sec:authentication property="principal" var="user"/>
<div>
Olá ${user.name }
</div>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_ADMIN')">
 <c:url value="/products/form" var="formLink" />
 <a href="${formLink }">
 	Cadastrar novo produto
 </a>
</sec:authorize>

${SUCCESS}
	<table>
		<tr>
			<th>Título</th>
			<th>Descrição</th>
			<th>Número de Páginas</th>
			<th>Preço - Ebook</th>
			<th>Preço - Printed</th>
			<th>Preço - Combo</th>
			<th>Detalhes</th>
		</tr>
		
			<c:forEach items="${products}" var="product" varStatus="status">
				<tr>
					<td>${product.title}</td>
					<td>${product.description }</td>
					<td>${product.numberOfPages}</td>
					<c:forEach items="${product.prices}" var="price" varStatus="status">
						<td>R$ ${price.value}</td>
					</c:forEach>
					<td>
						<c:url value="/products/${product.id}" var="linkDetalhar" />
						<a href="${linkDetalhar }">Detalhar</a>
					</td>
				</tr>
			</c:forEach>
		
	</table>
	</cdc:page>
	
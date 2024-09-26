<%@ include file="header.jsp" %>


   <h1><fmt:message key="msg.province.title" /></h1>
   <a href="provinces?action=new"><fmt:message key="msg.province.add" /></a>
   <table border="1">
       <thead>
           <tr>
               <th><fmt:message key="msg.province.id" /></th>
               <th><fmt:message key="msg.province.code" /></th>
               <th><fmt:message key="msg.province.name" /></th>
               <th><fmt:message key="msg.province.actions" /></th>
           </tr>
       </thead>
       <tbody>
           <c:forEach var="province" items="${listProvinces}">
               <tr>
                   <td>${province.id}</td>
                   <td>${province.code}</td>
                   <td>${province.name}</td>
                   <td>
                       <a href="provinces?action=edit&id=${province.id}"><fmt:message key="msg.province.edit" /></a>
                       <form action="provinces" method="post" style="display:inline;">
                           <input type="hidden" name="action" value="delete" />
                           <input type="hidden" name="id" value="${province.id}" />
                           <!-- Cuando una cadena de caracteres incluye a otra esta debe llevar comillas simples -->
                           <input type="submit" value="<fmt:message key='msg.province.delete' />" onclick="return confirm('<fmt:message key='msg.province.confirm' />')" />
                       </form>
                   </td>
               </tr>
           </c:forEach>
       </tbody>
   </table>
<%@ include file="footer.jsp" %>

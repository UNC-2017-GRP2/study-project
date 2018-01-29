<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Project</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/profile-style.css">
    <script type="text/javascript" src="webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap-form-helpers/2.3.0/js/bootstrap-formhelpers-phone.js"></script>
    <script type="text/javascript">
        <%@include file="/resources/js/profile-js.js" %>
    </script>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="container" style="margin-left: 25%;">
    <div class="row">
        <div class="col-md-7">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-4">
                            <h4>User Profile</h4>
                        </div>
                        <div class="col-md-4 col-md-offset-4" style="text-align: right;">
                            <button type="button" class="btn btn-default" data-toggle="modal"
                                    data-target="#formEditProfileModal">Edit profile
                            </button>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="box box-info">
                        <div class="box-body">
                            <div class="col-sm-6">
                                <div align="center"><img alt="User Pic"
                                                         src="https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg"
                                                         id="profile-image1" class="img-circle img-responsive">
                                    <input id="profile-image-upload" class="hidden" type="file">
                                    <div style="color:#999;">click here to change profile image</div>
                                    <!--Upload Image Js And Css-->
                                </div>
                                <br>
                                <!-- /input-group -->
                            </div>
                            <div class="col-sm-6">
                                <h4 style="color:#00b1b1;">${user.login}</h4></span>
                                <!--<span><p>User</p></span>-->
                            </div>
                            <div class="clearfix"></div>
                            <hr style="margin:5px 0 5px 0;">

                            <div class="col-sm-5 col-xs-6 tital ">Full Name:</div>
                            <div class="col-sm-7 col-xs-6 ">${user.fio}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 tital ">Username:</div>
                            <div class="col-sm-7">${user.login}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 tital ">E-mail:</div>
                            <div class="col-sm-7">${user.email}</div>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 tital ">Phone Number:</div>
                            <c:choose>
                                <c:when test="${user.phoneNumber != null}">
                                    <div class="col-sm-7">${user.phoneNumber}</div>
                                </c:when>
                                <c:when test="${user.phoneNumber == null}">
                                    <div class="col-sm-7">${nullParameter}</div>
                                </c:when>
                            </c:choose>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 tital ">Date Of Birth:</div>
                            <c:choose>
                                <c:when test="${user.birthday != null}">
                                    <div class="col-sm-7">${user.birthday}</div>
                                </c:when>
                                <c:when test="${user.birthday == null}">
                                    <div class="col-sm-7">${nullParameter}</div>
                                </c:when>
                            </c:choose>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 tital ">My Address:</div>
                            <c:choose>
                                <c:when test="${userAddresses != null}">
                                    <div class="col-sm-7">
                                        <c:forEach items="${userAddresses}" var="address">
                                            ${address}
                                            <br>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:when test="${userAddresses == null}">
                                    <div class="col-sm-7">${nullParameter}</div>
                                </c:when>
                            </c:choose>
                            <div class="clearfix"></div>
                            <div class="bot-border"></div>

                            <div class="col-sm-5 col-xs-6 tital ">My Bank Card:</div>
                            <%--<c:choose>
                                <c:when test="${user.bankCard != null}">
                                    <div class="col-sm-7">${user.bankCard}</div>
                                </c:when>
                                <c:when test="${user.bankCard == null}">
                                    <div class="col-sm-7">${nullParameter}</div>
                                </c:when>
                            </c:choose>--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $(function () {
                $('#profile-image1').on('click', function () {
                    $('#profile-image-upload').click();
                });
            });
        </script>
    </div>
</div>


<div class="modal fade" id="formEditProfileModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Edit Profile</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="resetNewAddress">&times;</span>
                </button>
            </div>
            <div class="well" style="height: auto!important;">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#editProfile" data-toggle="tab">Profile</a></li>
                    <li><a href="#editPassword" data-toggle="tab">Password</a></li>
                    <li><a href="#editAddress" data-toggle="tab">Addresses</a></li>
                    <li><a href="#editBankCard" data-toggle="tab">Bank Cards</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane active in" id="editProfile">
                        <form:form action="/edit" method="POST" modelAttribute="userForUpdate" class="form-horizontal"
                                   id="editProfileForm" role="form">
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="login" class="col-xs-4 control-label">Username:</label>
                                    <div class="col-xs-6">
                                        <form:input type='text' id='login' path="login"
                                                    class="form-control"></form:input>
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <form:errors path="login"></form:errors>
                                </div>
                                <div class="form-group">
                                    <label for="fio" class="col-xs-4 control-label">Full Name:</label>
                                    <div class="col-xs-6">
                                        <form:input type='text' id='fio' path="fio" class="form-control"></form:input>
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <form:errors path="fio"></form:errors>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="col-xs-4 control-label">E-mail:</label>
                                    <div class="col-xs-6">
                                        <form:input type='text' id='email' path="email"
                                                    class="form-control"></form:input>
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <form:errors path="email"></form:errors>
                                </div>
                                <div class="form-group">
                                    <label for="phoneNumber1" class="col-xs-4 control-label">Phone Number:</label>
                                    <div class="col-xs-6">
                                        <form:input type='text' id='phoneNumber1' path="phoneNumber"
                                                    class="form-control input-medium bfh-phone" data-format="+7 (ddd) ddd-dddd"></form:input>
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <form:errors path="phoneNumber"></form:errors>
                                </div>
                                    <%--<div class="form-group">
                                        <label for="address1" class="col-xs-4 control-label">Address:</label>
                                        <div class="col-xs-6">
                                            <form:input type='text' id='address1' path="address"
                                                        class="form-control"></form:input>
                                        </div>
                                    </div>
                                    <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                        <form:errors path="address"></form:errors>
                                    </div>
                                    <div class="form-group">
                                        <label for="bankCard1" class="col-xs-4 control-label">Bank Card:</label>
                                        <div class="col-xs-6">
                                            <form:input type='text' id='bankCard1' path="bankCard"
                                                        class="form-control"></form:input>
                                        </div>
                                    </div>
                                    <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                        <form:errors path="bankCard"></form:errors>
                                    </div>--%>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary">Update</button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form:form>
                    </div>
                    <div class="tab-pane fade" id="editPassword">
                        <form:form action="/editPassword" method="POST" class="form-horizontal" id="editPasswordForm"
                                   role="form" modelAttribute="userForUpdate">
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="oldPassword" class="col-xs-4 control-label">Old Password:</label>
                                    <div class="col-xs-6">
                                        <input type='password' id='oldPassword' name="oldPassword" class="form-control">
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <label class="validationMessage errorOldPassword"></label>
                                    <c:choose>
                                        <c:when test="${errorOldPassword}">
                                            <script>putValueToErrorPasswordLabel("Old password is not correct");</script>
                                        </c:when>
                                        <c:otherwise>
                                            <script>putValueToErrorPasswordLabel("");</script>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="form-group">
                                    <label for="passwordHash" class="col-xs-4 control-label">New Password:</label>
                                    <div class="col-xs-6">
                                        <form:input type='password' id='passwordHash' path="passwordHash"
                                                    class="form-control"></form:input>
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <form:errors path="passwordHash"></form:errors>
                                </div>
                                <div class="form-group">
                                    <label for="confirmPassword" class="col-xs-4 control-label">Confirm
                                        Password:</label>
                                    <div class="col-xs-6">
                                        <form:input type='password' id='confirmPassword' path="confirmPassword"
                                                    class="form-control"></form:input>
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <form:errors path="confirmPassword"></form:errors>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary">Update</button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form:form>
                    </div>
                    <div class="tab-pane fade" id="editAddress">
                        <form:form action="/editAddresses" method="GET" class="form-horizontal" id="editAddressForm"
                                   role="form">
                            <div class="modal-body">
                                <div class="list-group">
                                    <ul>
                                        <c:if test="${newAddresses != null}">
                                            <c:forEach items="${newAddresses}" var="address">
                                                <li class="list-group-item">
                                                    <div class="col-xs-11 text-left">
                                                        <h4>${address}</h4>
                                                    </div>
                                                    <div class="col-xs-1 text-right">
                                                        <span aria-hidden="true" class="remove-address" address="${address}" onclick="removeAddress('${address}',this);">&times;</span>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </c:if>
                                        <li class="forNewAddress"></li>
                                    </ul>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <input type='text' id='input-address' class="form-control"
                                               placeholder="Enter the address...">
                                    </div>
                                    <div class="col-xs-2">
                                        <button type="button" class="btn btn-default" onclick="addAddress();">Add address</button>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label id="addressValid"></label>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary">Update</button>
                                <button type="button" class="btn btn-secondary resetNewAddress" data-dismiss="modal">Cancel</button>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form:form>
                    </div>
                    <div class="tab-pane fade" id="editBankCard">

                    </div>
                </div>
            </div>
        </div>
        <c:if test="${pageContext.request.userPrincipal.name != null}">
        <table>
            <sec:authorize access="hasRole('ROLE_COURIER')">
                <tr>
                    <td>
                        <a href="<c:url value='/free-orders'/>">Free orders</a>
                    </td>
                </tr>
            </sec:authorize>
        </table>
        </c:if>

        <script>${flag}</script>

</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorator="layout/template">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <head>

        <meta charset="utf-8">
        <meta name="author" content="">
        <title>Job applicants | FindJobs.com</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <style type="text/css">
            #requirements {
                margin-top: 10px;
                padding-top: 50px;
                padding-bottom: 50px;
                color: #fff;
                background-color: #3F729B;
            }

            #team {
                margin-top: 10px;
                padding-top: 50px;
                padding-bottom: 50px;
                color: #fff;
                background-color: #2E2E2E;
            }

            .jumbotron {
                margin-bottom: 10px;
            }

            .a1{
                font-size: 24px;
            }
            /* Full-width input fields */
            input[type=text], input[type=password] {
                width: 100%;
                padding: 12px 20px;
                margin: 8px 0;
                display: inline-block;
                border: 1px solid #ccc;
                box-sizing: border-box;
            }

            .results {
                margin: 0px 100px 0px 100px;
                padding: 50px 100px 10px 100px;
                border-right: 1px solid #bdbdbd;
                border-left: 1px solid #bdbdbd;
            }
            /* Set a style for all buttons */
            button {
                background-color: #4CAF50;
                color: white;
                padding: 14px 20px;
                margin: 8px 0;
                border: none;
                cursor: pointer;
                width: 100%;
            }

            button:hover {
                opacity: 0.8;
            }

            /* Extra styles for the cancel button */
            .cancelbtn {
                width: auto;
                padding: 10px 18px;
                background-color: #f44336;
            }

            /* Center the image and position the close button */
            .imgcontainer {
                text-align: center;
                margin: 24px 0 12px 0;
                position: relative;
            }

            .groups{
                line-height: 90%;
            }

            img.avatar {
                width: 10%;
                border-radius: 50%;
            }

            .container {
                padding: 16px;
            }

            span.psw {
                float: right;
                padding-top: 16px;
            }

            /* The Modal (background) */
            .modal {
                display: none; /* Hidden by default */
                position: fixed; /* Stay in place */
                z-index: 1; /* Sit on top */
                left: 0;
                top: 0;
                width: 100%; /* Full width */
                height: 100%; /* Full height */
                overflow: auto; /* Enable scroll if needed */
                background-color: rgb(0, 0, 0); /* Fallback color */
                background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
                padding-top: 60px;
            }

            /* Modal Content/Box */
            .modal-content {
                background-color: #fefefe;
                margin: 5% auto 15% auto;
                /* 5% from the top, 15% from the bottom and centered */
                border: 1px solid #888;
                width: 40%; /* Could be more or less, depending on screen size */
            }

            /* The Close Button (x) */
            .close {
                position: absolute;
                right: 25px;
                top: 0;
                color: #000;
                font-size: 35px;
                font-weight: bold;
            }

            .close:hover, .close:focus {
                color: red;
                cursor: pointer;
            }

            /* Add Zoom Animation */
            .animate {
                -webkit-animation: animatezoom 0.6s;
                animation: animatezoom 0.6s
            }

            @
            -webkit-keyframes animatezoom {
                from {-webkit-transform: scale(0)
                }

                to {
                    -webkit-transform: scale(1)
                }

            }
            @
            keyframes animatezoom {
                from {transform: scale(0)
                }

                to {
                    transform: scale(1)
                }

            }

            /* Change styles for span and cancel button on extra small screens */
            @media screen and (max-width: 300px) {
                span.psw {
                    display: block;
                    float: none;
                }
                .cancelbtn {
                    width: 100%;
                }
            }

            .row.vertical-divider {
                overflow: hidden;
            }

            .row.vertical-divider>div[class^="col-"] {
                text-align: center;
                padding-top: 5%;
                /*padding-bottom: 100px;*/
                margin-top: 30px;
                /*margin-bottom: -100px;*/
                border-left: 3px solid #689f38;
                border-right: 3px solid #689f38;
            }

            .row1 {
                overflow: hidden;
            }

            .row2 {
                margin-bottom: -99999px;
                padding-bottom: 99999px;
            }

            body {
                padding-top: 20px;
                padding-bottom: 20px;
            }
        </style>


    </head>

    <body id="pagetop">

        <div class="container-fluid">

            <nav class="navbar navbar-inverse navbar-fixed-top">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="/jobboard/index">BUSE Recruitment</a>
                    </div>
                    <ul class="nav navbar-nav navbar-right">

                        <li class="dropdown"><a class="dropdown-toggle"
                                                data-toggle="dropdown" href="#">logged in as
                                BUSE <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="/jobboard/company/profile/${company.companyId}">Profile</a></li>
                                <li><a href="/jobboard/index">Logout</a></li>
                            </ul></li>
                    </ul>
                </div>
            </nav>

            <div class="jumbotron">
                <div class="container text-center">
                    <div class="container-fluid" style="margin-top: 40px">${message}</div>

                    <h2>Job Applicants for ${job.title}</h2>

                </div>
            </div>

            <div class="row col-sm-12" style="margin: auto">
                <form class="col-sm-6" action="/jobboard/application/shortList" method="get">
                    <input type="hidden" name="jobId" value="${job.jobId}"></input>

                    <button type="submit" class="btn btn-block btn-primary pagination-centered">AUTO SHORTLIST APPLICANTS</button>
                </form>

            </div>
                    <br/>
                    <br/>
            <div class="results">

                <br>

                <c:forEach items="${applicants}" var="applicants">
                    <a class="a1" href="/company/showjob?cid=${company.companyId}&jobId=${job.jobId}">Add to short list</a>
                    <div class="row">
                        <div class="col-sm-4 groups">
                            <p>
                                <b>Name:</b> ${applicants.firstName}
                            </p>
                            <p>
                                <b>Surname:</b> ${applicants.lastName}
                            </p>
                            <p>
                                <b>Age:</b> ${applicants.age}
                            </p>
                            <p>
                                <b>Email:</b> ${applicants.emailId}
                            </p>

                            <p>
                                <b>Highest Education:</b> 
                                <c:if test="${applicants.highestEducation == 1}">
                                    <c:out value="Master's degree ${applicants.edClass}" />
                                </c:if>
                                <c:if test="${applicants.highestEducation == 0}">
                                    <c:out value="Bachelor's degree ${applicants.edClass}" />
                                </c:if>
                                <c:if test="${applicants.highestEducation == 2}">
                                    <c:out value="Ph.D. ${applicants.edClass}" />
                                </c:if>
                            </p>
                        </div>

                    </div>
                    <hr />
                </c:forEach>
                <!-- <p>Posted by: <c:out value="${job.company}"></c:out></p>
                    <p>Status: <c:out value="${job.location}"></c:out></p>
                    <p>Status: <c:out value="${job.salary}"></c:out></p>
                    <p>${job.description}</p>
                -->
            </div>


            <div class="jumbotron">
                <div class="container text-center">


                    <h2>Short Listed</h2>

                </div>
            </div>

            <div class="results">
                <h4>${shortListed.size()} Shortlisted Applicants for ${job.title}</h4>

                <div class="row col-sm-12" style="margin: auto">
                    <div class="col-sm-6">
                        <form action="/jobboard/createinterview" method="post">
                        <input type="hidden" name="jobId" value="${job.jobId}">
                            <label><input type="datetime-local" placeholder="Interview date" class="form-control" name="datetime">Date</label>
                            <label><button type="submit" class="btn btn-block btn-primary">Notify Interview</button>Applicants will be notified on email</label>
                        </form>
                    </div>
                    <form class="col-sm-6" action="/jobboard/showexam" method="get">
                        <input type="hidden" name="jobId" value="${job.jobId}">
                        <button type="submit" class="btn btn-block btn-info">CREATE/VIEW EXAM</button>
                    </form>

                </div>
                        <br>
                        <br>

                <c:forEach items="${shortListed}" var="shortListed">
                    <h5 href="/company/showjob?cid=${company.companyId}&jobId=${job.jobId}">Remove from short list</h5>
                    <div class="row">
                        <div class="col-sm-4 groups">
                            <p>
                                <b>Name:</b> ${shortListed.firstName}
                            </p>
                            <p>
                                <b>Surname:</b> ${shortListed.lastName}
                            </p>
                            <p>
                                <b>Age:</b> ${shortListed.age}
                            </p>
                            <p>
                                <b>Email:</b> ${shortListed.emailId}
                            </p>

                            <p>
                                <b>Highest Education:</b> 
                                <c:if test="${shortListed.highestEducation == 1}">
                                    <c:out value="Master's degree  ${shortListed.edClass}" />
                                </c:if>
                                <c:if test="${shortListed.highestEducation == 0}">
                                    <c:out value="Bachelor's degree  ${shortListed.edClass}" />
                                </c:if>
                                <c:if test="${shortListed.highestEducation == 2}">
                                    <c:out value="Ph.D.  ${shortListed.edClass}" />
                                </c:if>
                            </p>
                        </div>

                    </div>
                    <hr />
                </c:forEach>
                <!-- <p>Posted by: <c:out value="${job.company}"></c:out></p>
<p>Status: <c:out value="${job.location}"></c:out></p>
<p>Status: <c:out value="${job.salary}"></c:out></p>
<p>${job.description}</p>
                -->

            </div>


        </div>
    </div>

    <script>
        // Get the modal
        var modal = document.getElementById('id01');

        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function (event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
    </script>

</body>

</html>
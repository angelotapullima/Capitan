<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Capitán</title>
    <link rel="shortcut icon" href="<?php echo _SERVER_ . _ICON_;?>">
    <link href="<?php echo _SERVER_ . _STYLES_ADMIN_;?>css/bootstrap.min.css" rel="stylesheet">
    <link href="<?php echo _SERVER_ . _STYLES_ADMIN_;?>css/font-awesome.min.css" rel="stylesheet">
    <link href="<?php echo _SERVER_ . _STYLES_ADMIN_;?>css/datepicker3.css" rel="stylesheet">
    <link href="<?php echo _SERVER_ . _STYLES_ADMIN_;?>css/datatables.min.css" rel="stylesheet">
    <link href="<?php echo _SERVER_ . _STYLES_ADMIN_;?>css/styles.css" rel="stylesheet">
    <!-- Alertify -->
    <script src="<?php echo  _SERVER_ . _STYLES_ALL_;?>alertifyjs/alertify.js"></script>
    <link rel="stylesheet" type="text/css" href="<?php echo _SERVER_ . _STYLES_ALL_;?>charts/Chart.min.css">
    <link rel="stylesheet" type="text/css" href="<?php echo _SERVER_ . _STYLES_ALL_;?>alertifyjs/css/alertify.css">
    <link rel="stylesheet" type="text/css" href="<?php echo _SERVER_ . _STYLES_ALL_;?>alertifyjs/css/themes/default.css">
    <!--Custom Font-->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
    <style>
        .loader {
            position: fixed;
            left: 0px;
            top: 0px;
            width: 100%;
            height: 100%;
            z-index: 99999;
            background: url('<?= _SERVER_?>media/loader.gif') 50% 50% no-repeat rgb(249,249,249);
            opacity: .8;
        }
        input[type="file"]#imagen {
            width: 0.1px;
            height: 0.1px;
            opacity: 0;
            overflow: hidden;
            position: absolute;
            z-index: -1;
        }
        label[for="imagen"] {
            margin-top: 5px;
            margin-left: 8px;
            font-size: 12px;
            color: #fff;
            background-color: #7ea878;
            display: inline-block;
            transition: all .5s;
            cursor: pointer;
            padding: 5px 10px !important;
            width: fit-content;
            text-align: center;
            border-radius: 10px;
            word-break: break-all;
        }
    </style>
</head>

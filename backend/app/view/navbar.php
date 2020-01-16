<body>
<nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse"><span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span></button>
            <a class="navbar-brand" href="#">Capitán</a>
            <ul class="nav navbar-top-links navbar-right">

                <li class="dropdown">
                    <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                        <em class="fa fa-user"></em>
                    </a>
                    <ul class="dropdown-menu dropdown-alerts">
                        <li>
                            <a href="<?php echo _SERVER_;?>Edit/info">
                                <div>
                                    <em class="fa fa-gear"></em> Editar Usuario
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="<?php echo _SERVER_;?>Edit/changeUser">
                                <div>
                                    <em class="fa fa-pencil-square-o"></em> Cambiar Contraseña
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="<?php echo _SERVER_;?>logout/singOut">
                                <div>
                                    <em class="fa fa-sign-in"></em> Cerrar Sesión
                                </div>
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div><!-- /.container-fluid -->
</nav>

<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
    <div class="profile-sidebar">
        <div class="profile-userpic">
            <img src="<?php echo _SERVER_;?><?php echo $this->crypt->decrypt($_SESSION['user_image'], _FULL_KEY_);?>" class="img-responsive" alt="">
        </div>
        <div class="profile-usertitle">
            <div class="profile-usertitle-name"><?php echo $this->crypt->decrypt($_SESSION['user_nickname'], _FULL_KEY_);?></div>
            <div class="profile-usertitle-status"><span class="indicator label-success"></span>Conectado</div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="divider"></div>

    <ul class="nav menu">
        <?php
        $raioz = 1;
        foreach ($navs as $nav){
            $activom = '';
            if($nav->menu_controller == $_SESSION['controlador']){
                $activom = "active";
                $_SESSION['controlador'] = $nav->menu_name;
                $_SESSION['icono'] = $nav->menu_icon;
                //Obtener el Nombre del Controlador y de la Funcion
                $name = $this->nav->listOptionName($_SESSION['controlador'], $_SESSION['accionnav']);
                (isset($name->optionm_name))? $_SESSION['accion'] = $name->optionm_name:$_SESSION['accion'] = "";
            }
            ?>
            <li class="parent <?php echo $activom;?>"><a data-toggle="collapse" href="#sub-item-<?php echo $raioz;?>">
                    <em class="<?php echo $nav->menu_icon;?>">&nbsp;</em> <?php echo $nav->menu_name;?> <span data-toggle="collapse" href="#sub-item-<?php echo $raioz;?>" class="icon pull-right"><em class="fa fa-plus"></em></span>
                </a>
                <ul class="children collapse" id="sub-item-<?php echo $raioz;?>">
                    <?php
                    $option = $this->nav->listOptions($nav->id_menu);
                    foreach ($option as $o){
                        ?>
                        <li>
                            <a class="" href="<?php echo _SERVER_. $nav->menu_controller . '/'. $o->optionm_function;?>">
                                <span class="fa fa-circle-o">&nbsp;</span> <?php echo $o->optionm_name;?>
                            </a>
                        </li>
                        <?php
                    }
                    ?>
                </ul>
            </li>
            <?php
            $raioz++;
        }
        ?>

        <!--<li class="active"><a href="index.html"><em class="fa fa-dashboard">&nbsp;</em> Dashboard</a></li>
        <li><a href="widgets.html"><em class="fa fa-calendar">&nbsp;</em> Widgets</a></li>
        <li><a href="charts.html"><em class="fa fa-bar-chart">&nbsp;</em> Charts</a></li>
        <li><a href="elements.html"><em class="fa fa-toggle-off">&nbsp;</em> UI Elements</a></li>
        <li><a href="panels.html"><em class="fa fa-clone">&nbsp;</em> Alerts &amp; Panels</a></li>-->


        <!--<li><a href="login.html"><em class="fa fa-power-off">&nbsp;</em> Logout</a></li>-->
    </ul>
</div><!--/.sidebar-->

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                    <em class="<?php echo $_SESSION['icono'];?>"></em>
                </a></li>
            <li class="active"><?php echo ucfirst($_SESSION['controlador']);?></li>
        </ol>
    </div><!--/.row-->
    <!--
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><?php echo ucfirst($_SESSION['controlador']);?> - <?php echo $_SESSION['accion'];?></h1>
        </div>
    </div>--><!--/.row-->




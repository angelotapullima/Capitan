<?php
require 'fpdf.php';
class PDFF extends FPDF{
    function Header(){
        $this->Image('media/logosaneamiento.png',5,5,20);
        $this->Image('media/em.png',25,5,20);
        $this->SetFont('Arial','B',10);
        $this->Cell(30);
        $this->Cell(165,10,'SUB GERENCIA DE SANEAMIENTO',0,1,'R');
        $this->SetFont('Arial','B',12);
        $this->Cell(190,10,'MUNICIPALIDAD PROVINCIAL DE MAYNAS',0,0,'C');
        $this->Ln();
    }
    function Footer(){
        $fecha=date('Y-m-d H:i:s');
        $this->SetY(-15);
        $this->SetFont('Arial','I',8);
        $this->Cell(90,10,'Reporte generado el: '.$fecha,0,0,'L');
        $this->Cell(20,10,'Pagina ' . $this->PageNo().'/{nb}',0,0,'C');
    }
}
?>
package com.example.db_sabor_y_aroma.model;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class ExcelGenerator {

    // Helper seguro para leer campos de Reporte o de su relación EstadoReporte
    private static String obtenerValor(Object obj, String... metodos) {
        if (obj == null) return "";
        for (String nombreMetodo : metodos) {
            try {
                Method m = obj.getClass().getMethod(nombreMetodo);
                Object val = m.invoke(obj);
                if (val != null) {
                    if (!(val instanceof String || val instanceof Number || val instanceof java.util.Date || val instanceof java.time.temporal.Temporal)) {
                        String sub = obtenerValor(val, "getNombreEstado", "getNombre", "getEspecialidad");
                        if (!sub.isEmpty()) return sub;
                    }
                    return val.toString();
                }
            } catch (Exception ignored) {}
        }
        return "";
    }

    public static ByteArrayInputStream reportesToExcel(List<Reporte> registros, String filtroTipo) throws IOException {
        String[] COLUMNS = {"ID", "Fecha", "Tipo de Reporte", "Especialidad / Estado", "Descripción"};

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Historial de Cambios");

            // 1. Fila de Título Principal
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Reporte General de Registros - Filtro: " + (filtroTipo != null ? filtroTipo : "Todos"));

            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 14);
            titleStyle.setFont(titleFont);
            titleCell.setCellStyle(titleStyle);

            // 2. Fila de Encabezados (Fila 2)
            Row headerRow = sheet.createRow(2);
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            for (int col = 0; col < COLUMNS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNS[col]);
                cell.setCellStyle(headerStyle);
            }

            // 3. Llenar filas filtrando si corresponde
            int rowIndex = 3;
            for (Reporte reg : registros) {
                String id = obtenerValor(reg, "getId", "getIdReporte");
                String fecha = obtenerValor(reg, "getFecha", "getFechaReporte");
                String tipo = obtenerValor(reg, "getTipoReporte", "getTipo");
                if (tipo.isEmpty()) tipo = "Registro";

                // Si el usuario eligió un filtro distinto a "Todos", filtramos aquí
                if (filtroTipo != null && !filtroTipo.equalsIgnoreCase("Todos") && !tipo.equalsIgnoreCase(filtroTipo)) {
                    continue;
                }

                String especialidad = obtenerValor(reg, "getEspecialidad", "getEstadoReporte", "getAccion");
                if (especialidad.isEmpty()) especialidad = "Operación";

                String descripcion = obtenerValor(reg, "getDescripcion", "getDetalle", "getMensaje");

                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue("#" + id);
                row.createCell(1).setCellValue(fecha);
                row.createCell(2).setCellValue(tipo);
                row.createCell(3).setCellValue(especialidad);
                row.createCell(4).setCellValue(descripcion);
            }

            for (int i = 0; i < COLUMNS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
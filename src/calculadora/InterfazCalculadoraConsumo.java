import javax.swing.*;//Proporciona clases y componentes para construir interfaces gráficas de usuario.
import javax.swing.table.DefaultTableModel;//Proporciona un modelo de tabla predeterminado para JTable, que permite mostrar y manipular datos en forma de tabla.
import java.awt.*;//Proporciona clases y componentes para crear y gestionar la interfaz de usuario.
import java.awt.event.ActionEvent;//Se utilizan para manejar eventos de acción en los componentes de la interfaz de usuario, como botones.
import java.awt.event.ActionListener;//Se utilizan para manejar eventos de acción en los componentes de la interfaz de usuario, como botones.
import java.io.FileOutputStream;//Se utilizan para escribir datos en un archivo de salida y manejar excepciones relacionadas con la E/S de archivos.
import java.io.IOException;//Se utilizan para escribir datos en un archivo de salida y manejar excepciones relacionadas con la E/S de archivos.
import java.text.SimpleDateFormat;//Formato para el nombre del excel creado
import java.util.ArrayList;
import java.util.Date;//Fecha
import javax.swing.table.DefaultTableCellRenderer;//Centrar tabla
import javax.swing.table.TableColumnModel;//Proporciona métodos para administrar las columnas de una tabla.
import org.apache.poi.ss.usermodel.*;//reporte excel
import org.apache.poi.xssf.usermodel.XSSFWorkbook;//reporte excel
import javax.swing.JFileChooser;//Seleccionar ruta para Reporte






public class InterfazCalculadoraConsumo extends JFrame {
    private JComboBox<String> categoriaComboBox;//Tipos de datos seleccionados
    private JComboBox<String> electrodomesticoComboBox;//Tipos de datos seleccionados
    private JComboBox<String> tiempoComboBox;//Tipos de datos seleccionados
    private JComboBox<Integer> horasComboBox; //Tipos de datos seleccionados
    private JButton calcularButton;  //realizar calculo
    private JButton limpiarButton; //Limpiar seleccion
    private JButton agregarButton;  // Botón Agregar electrodoméstico
    private JLabel resultadoLabel; //Mostrar el resultado
    private JTable electrodomesticosTable;  // Tabla para mostrar los electrodomésticos agregados
    private double sumaConsumo = 0;  //suma de total de tabla
    private double sumaCosto = 0;    //suma de total de tabla
    private JLabel sumaLabel;  //Suma total de cosumo y costo
    private JButton vaciarTablaButton; //Boton para vaciar tabla
    private JButton seleccionarRutaButton; //Seleccionar ruta de guardado reporte
    private String rutaSeleccionada; //Guardar como string la ruta seleccionada


    private final CalculadoraConsumo calculadora; 

    public InterfazCalculadoraConsumo() {
        // Crear instancia de la calculadora
        calculadora = new CalculadoraConsumo();
        new ArrayList<>();

        // Configurar la ventana
        setTitle("Calculadora de Consumo");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        

        // Crear panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        add(panelPrincipal, BorderLayout.CENTER);

        // Crear componentes de la interfaz
        categoriaComboBox = new JComboBox<>(calculadora.getCategorias());
        electrodomesticoComboBox = new JComboBox<>();
        tiempoComboBox = new JComboBox<>(new String[]{"Seleccionar", "1 día", "1 semana", "1 mes", "2 meses", "3 meses", "4 meses", "5 meses", "6 meses", "7 meses", "8 meses", "9 meses", "10 meses", "11 meses", "12 meses"});
        horasComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24});
        calcularButton = new JButton("Calcular");
        limpiarButton = new JButton("Limpiar selección");
        agregarButton = new JButton("Agregar electrodoméstico");  // Nuevo botón
        resultadoLabel = new JLabel(""); //En blanco no se usa antes sí mostrar en infertaz
        //"El consumo del electrodoméstico es: "        
        seleccionarRutaButton = new JButton("Seleccionar Ruta");
        JButton generarReporteButton = new JButton("Generar reporte");
        vaciarTablaButton = new JButton("Vaciar tabla");
        
        


                // Crear tabla para mostrar los electrodomésticos agregados
        electrodomesticosTable = new JTable(new DefaultTableModel(
                new Object[]{"Electrodoméstico", "Tiempo", "Horas" , "Consumo (Kw/h)", "Costo (soles)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que la tabla sea no editable para evitar modificaciones directas
                return false;
            }
        });


        // Agregar componentes al panel principal
        //Label categorio
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        panelPrincipal.add(new JLabel("Categoría:"), gbc);
        //Menu desplegable categoria
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelPrincipal.add(categoriaComboBox, gbc);
        
        //Label Electrodomestico
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        panelPrincipal.add(new JLabel("Electrodoméstico:"), gbc);
        //Menus desplegable elctrodomestico
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelPrincipal.add(electrodomesticoComboBox, gbc);
        
        //Label tiempo
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        panelPrincipal.add(new JLabel("Tiempo:"), gbc);
        //Menus desplegable del tiempo
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelPrincipal.add(tiempoComboBox, gbc);
        
        //Label hora por dia
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        panelPrincipal.add(new JLabel("Horas por día:"), gbc);
        //Menus desplegable de horas
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelPrincipal.add(horasComboBox, gbc);
        
        //Posicion del boton calcular
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridwidth = 2;
        panelPrincipal.add(calcularButton, gbc);
        
        //posicion limpiar selección
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridwidth = 2;
        panelPrincipal.add(limpiarButton, gbc);
       
        
        //Resultado label en interfaz no se usa
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridwidth = 2;
        panelPrincipal.add(resultadoLabel, gbc);

        // Agregar tabla al panel principal
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelPrincipal.add(new JScrollPane(electrodomesticosTable), gbc);

        // Agregar botón "Agregar electrodoméstico" al panel principal
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(agregarButton, gbc);
        
        
       // Crear el JLabel sumaLabel
       sumaLabel = new JLabel();

       // Agregar sumaLabel al panel principal
        gbc.gridx = 1;  // Cambiar el valor de gridx a 1 para alinear a la derecha
        gbc.gridy = 8;
        gbc.gridwidth = 1;  // Cambiar el valor de gridwidth a 1 para ocupar solo una celda
        gbc.weightx = 0;  // Establecer weightx en 0 para que no se expanda horizontalmente
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;  // Establecer fill en NONE para que no se extienda
        gbc.anchor = GridBagConstraints.LINE_END;  // Establecer anchor en LINE_END para alinear a la derecha
        panelPrincipal.add(sumaLabel, gbc);

        //posicion boton vaciar tabla
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(vaciarTablaButton, gbc);
        
        
        
         // Ubicación el botón "Generar reporte"
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(generarReporteButton, gbc);


        
        //Posiccion boton selecciona ruta
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(seleccionarRutaButton, gbc);



        // Asociar eventos
        categoriaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarElectrodomesticos();
            }
        });

        calcularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularConsumo();
            }
            });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarSeleccion();
            }
          });

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarElectrodomestico();
            }
         });
        
        vaciarTablaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vaciarTabla();
            }
        });

                // Asociar el evento al botón "Generar reporte"
            generarReporteButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (rutaSeleccionada != null) {
                generarReporteExcel();
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una ruta para guardar el archivo Excel.", "Ruta no seleccionada", JOptionPane.WARNING_MESSAGE);
            }
        }
    });
            
            
            //Funcion boton Seleccionar Ruta de reporte
            seleccionarRutaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int result = fileChooser.showOpenDialog(InterfazCalculadoraConsumo.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        rutaSeleccionada = fileChooser.getSelectedFile().getAbsolutePath();
                    }
                }
            });

            
            
            // Mostrar la ventana
            // Centrar la ventana en medio de la pantalla
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = screenSize.width;
            int screenHeight = screenSize.height;

            int windowWidth = getWidth();
            int windowHeight = getHeight();

            int posX = (screenWidth - windowWidth) / 2;
            int posY = (screenHeight - windowHeight) / 2;

            setLocation(posX, posY);
            setVisible(true);



                // Mostrar la ventana
                setVisible(true);

            }

    
            private void actualizarElectrodomesticos() {
                int categoriaIndex = categoriaComboBox.getSelectedIndex();
                if (categoriaIndex >= 0) {
                    String[] electrodomesticos = calculadora.getElectrodomesticos(categoriaIndex);
                    electrodomesticoComboBox.setModel(new DefaultComboBoxModel<>(electrodomesticos));
                } else {
                    electrodomesticoComboBox.setModel(new DefaultComboBoxModel<>());
                }
            }

            private void calcularConsumo() {
                int categoriaIndex = categoriaComboBox.getSelectedIndex();
                int electrodomesticoIndex = electrodomesticoComboBox.getSelectedIndex();
                int tiempoIndex = tiempoComboBox.getSelectedIndex();
                int horasPorDia = (int) horasComboBox.getSelectedItem();

                if (categoriaIndex == -1 || electrodomesticoIndex == -1 || tiempoIndex <= 0 || horasPorDia == -1) {
                    JOptionPane.showMessageDialog(this, "Por favor, seleccione todos los datos requeridos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double consumoElectrodomestico = calculadora.getConsumoElectrodomestico(categoriaIndex, electrodomesticoIndex);
                double tiempoConsumo = calculadora.getTiempoConsumo(tiempoIndex);
                double consumoTotal = consumoElectrodomestico * horasPorDia * tiempoConsumo / 1000;
                double costoTotal = consumoTotal * 0.70; // Costo en soles por Kw/h


                // Obtener el resultado formateado con dos decimales
                String consumoTotalStr = String.format("%.2f", consumoTotal);
                String costoTotalStr = String.format("%.2f", costoTotal);

                // Construir el mensaje del cuadro de diálogo
                String mensaje = "El consumo del electrodoméstico es: " + consumoTotalStr + " Kw/h (Costo: " + costoTotalStr + " soles)";

                // Mostrar el cuadro de diálogo con el mensaje
                JOptionPane.showMessageDialog(this, mensaje, "Resultado", JOptionPane.INFORMATION_MESSAGE);


            }


            //Limpiar selección interfaz
            private void limpiarSeleccion() {
                categoriaComboBox.setSelectedIndex(0);
                electrodomesticoComboBox.setSelectedIndex(-1);
                tiempoComboBox.setSelectedIndex(0);
                horasComboBox.setSelectedIndex(0);
                //resultadoLabel.setText("El consumo del electrodoméstico es: ");no se usa, antes si resultado label para limpiar infertaz
            }

            //SUMA DE CONSUMO Y TOTAL
            private void actualizarLabelSuma() {
            sumaLabel.setText("Consumo Total: " + String.format("%.2f", sumaConsumo) + "     Costo Total: " + String.format("%.2f", sumaCosto));
            }

        

            private void generarReporteExcel() {
                
                // Crear un nuevo libro de Excel
                Workbook workbook = new XSSFWorkbook();

                // Crear una hoja de cálculo en el libro
                Sheet sheet = workbook.createSheet("Reporte de Consumo");

                // Crear estilo de celda para las celdas de la tabla
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);

                // Crear estilo de celda para la fila de encabezados
                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.cloneStyleFrom(cellStyle);
                headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                

                // Crear la primera fila para los encabezados de las columnas
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Electrodoméstico");
                headerRow.createCell(1).setCellValue("Tiempo");
                headerRow.createCell(2).setCellValue("Horas");
                headerRow.createCell(3).setCellValue("Consumo (Kw/h)");
                headerRow.createCell(4).setCellValue("Costo (soles)");

                // Aplicar estilo a las celdas de la fila de encabezados
                for (Cell headerCell : headerRow) {
                    headerCell.setCellStyle(headerStyle);
                }

                // Obtener el modelo de la tabla de electrodomésticos
                DefaultTableModel model = (DefaultTableModel) electrodomesticosTable.getModel();

                // Verificar si hay filas en el modelo
                if (model.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "No hay datos en la tabla para generar el reporte.", "Tabla Vacía", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Obtener el número de filas en la tabla
                int rowCount = model.getRowCount();

                // Iterar sobre las filas de la tabla y agregar los datos al archivo Excel
                for (int i = 0; i < rowCount; i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Cell cell = row.createCell(j);
                        cell.setCellValue(model.getValueAt(i, j).toString());
                        cell.setCellStyle(cellStyle);
                    }
                }

                // Agregar fila de totales
                Row totalRow = sheet.createRow(rowCount + 1);
                totalRow.createCell(0).setCellValue("Total");
                totalRow.createCell(3).setCellValue(String.format("%.2f Kw/h", sumaConsumo));
                totalRow.createCell(4).setCellValue(String.format("%.2f Soles", sumaCosto));
                for (Cell totalCell : totalRow) {
                    totalCell.setCellStyle(cellStyle);
                }

                // Ajustar automáticamente el ancho de las columnas
                for (int i = 0; i < 5; i++) {
                    sheet.autoSizeColumn(i);
                }
        
                // Obtener la fecha y hora actual
                Date fechaHoraActual = new Date();
                SimpleDateFormat formatoFechaHora = new SimpleDateFormat("yyyy-MM-dd");//Formato con Fecha y hora:"yyyy-MM-dd_HH-mm-ss"
                String fechaHoraActualString = formatoFechaHora.format(fechaHoraActual);

                // Generar el nombre del archivo con la fecha y hora actual
                String nombreArchivo = "reporte_consumo_" + fechaHoraActualString + ".xlsx";
                String rutaCompleta = rutaSeleccionada + "\\" + nombreArchivo;

                // Guardar el libro de Excel en el archivo seleccionado
                try (FileOutputStream outputStream = new FileOutputStream(rutaCompleta)) {
                    workbook.write(outputStream);
                    JOptionPane.showMessageDialog(null, "Se generó el reporte en Excel correctamente.", "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Ocurrió un error al generar el reporte en Excel.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                }


    
        private void agregarElectrodomestico() {
        int categoriaIndex = categoriaComboBox.getSelectedIndex();
        int electrodomesticoIndex = electrodomesticoComboBox.getSelectedIndex();
        int tiempoIndex = tiempoComboBox.getSelectedIndex();
        int horasPorDia = (int) horasComboBox.getSelectedItem();

        if (categoriaIndex == -1 || electrodomesticoIndex == -1 || tiempoIndex <= 0 || horasPorDia == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione todos los datos requeridos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double consumoElectrodomestico = calculadora.getConsumoElectrodomestico(categoriaIndex, electrodomesticoIndex);
        double tiempoConsumo = calculadora.getTiempoConsumo(tiempoIndex);
        double consumoTotal = consumoElectrodomestico * horasPorDia * tiempoConsumo / 1000;
        double costoTotal = consumoTotal * 0.70; // Costo en soles por Kw/h

        // Obtener el nombre del electrodoméstico seleccionado
        String electrodomestico = (String) electrodomesticoComboBox.getSelectedItem();

        // Obtener tiempo seleccionado y horas seleccionadas
        String tiempoSeleccionado = (String) tiempoComboBox.getSelectedItem();
        String horasSeleccionadas = horasPorDia + " Horas";
        
        // Crear una nueva fila para la tabla con los datos del electrodoméstico y el consumo calculado
        String consumoTotalStr = String.format("%.2f Kw/h", consumoTotal);
        String costoTotalStr = String.format("%.2f Soles", costoTotal);

        // Crear una nueva fila para la tabla con los datos del electrodoméstico y el consumo calculado
        Object[] fila = {electrodomestico, tiempoSeleccionado, horasSeleccionadas, consumoTotalStr, costoTotalStr};

        // Agregar la fila a la tabla
        DefaultTableModel model = (DefaultTableModel) electrodomesticosTable.getModel();
        model.addRow(fila);

        // Ajustar la columna del electrodoméstico al contenido sin expandirse hacia la izquierda
        TableColumnModel columnModel = electrodomesticosTable.getColumnModel();
        columnModel.getColumn(0).setResizable(false); // Desactivar la capacidad de redimensionamiento

        // Obtener el renderizador de la tabla para centrar los valores
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Aplicar el renderizador a las columnas que deseas centrar
        columnModel.getColumn(1).setCellRenderer(centerRenderer); // Consumo
        columnModel.getColumn(2).setCellRenderer(centerRenderer); // Costo
        columnModel.getColumn(3).setCellRenderer(centerRenderer); // Tiempo
        columnModel.getColumn(4).setCellRenderer(centerRenderer); // Horas

        // Ajustar automáticamente el ancho de las columnas restantes
        electrodomesticosTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);


        // Actualizar la suma del consumo y el costo
        sumaConsumo += consumoTotal;
        sumaCosto += costoTotal;

        // Actualizar el label correspondiente
        actualizarLabelSuma();
    }

    
        private void vaciarTabla() {
        DefaultTableModel model = (DefaultTableModel) electrodomesticosTable.getModel();
        model.setRowCount(0);  // Eliminar todas las filas de la tabla

        sumaConsumo = 0;  // Reiniciar suma de consumo
        sumaCosto = 0;    // Reiniciar suma de costo

        actualizarLabelSuma();  // Actualizar el label correspondiente
        }


        public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterfazCalculadoraConsumo interfaz = new InterfazCalculadoraConsumo();
                interfaz.actualizarLabelSuma(); // Llamada para mostrar el resultado inicial en 0
            }
        });
        }
    
    


        private static class CalculadoraConsumo {
            private final String[] categorias;
            private final String[][] electrodomesticos;
            private final double[][] consumos;

            public CalculadoraConsumo() {
                // Inicializar las matrices con los datos
                    categorias = new String[]{
                        "Seleccionar",
                        "PANTALLAS(monitores y televisores)",
                        "HORNOS MICROONDAS",
                        "LAVADORAS",
                        "COCINAS",
                        "VENTILADORES Y AIRE ACODICINADO",
                        "REFRIGERADORES",
                        "ESTEREOS, RADIOS",
                        "LICUADORAS",
                        "ELECTRICO(OLLA ARROCERA, FREIDORA DE AIRE, PARRILLA ELECTRICA)",
                        "ARTEFACTOS EXTRAS"
                };

                electrodomesticos = new String[][]{
                        {null},
                        // PANTALLAS (monitores y televisores)
                         {"(PC) Monitor de 15 pulgadas", "(PC) Monitor de 17 pulgadas", "(PC) Monitor de 19 pulgadas",  "TV de 24 pulgadas", "TV de 27 pulgadas", "TV de 32 pulgadas", "TV de 40 pulgadas", "TV de 42 pulgadas", "TV de 55 pulgadas", "TV de 75 pulgadas"},
                        // HORNOS MICROONDAS
                        {"Horno microondas", "Horno Microondas 20L", "Horno Microondas 30L", "Horno Microondas 25L", "Horno Microondas 32L", "Horno Microondas 40L", "Horno Electrico", "Horno tostador", "Horno rosticero electrico", "Horno convector electrico"},
                        // LAVADORAS
                        {"Lavadora 15 Kg", "Lavadora Carga Superior 17 Kg", "Lavadora Carga Superior 18Kg","Lavadora Carga Superior 19 Kg", "Lavadora Carga Superior 21 Kg", "Lavadora Carga Superior 24 Kg"},
                        // COCINAS
                        {"Cocina eléctrica de 2 hornillas", "Cocina eléctrica de 4 hornillas", "Cocina a Gas 6 Hornillas"},
                        // VENTILADORES Y AIRE ACONDICIONADO
                        {"Ventilador Circulador 16", "Ventilador doble 20", "Ventilador Torre 30", "Ventilador de pedestal", "Ventilador de pie 16", "Ventilador 18", "Aire Acondocionado 24, 30 a 40m2 inverter", "Aire Acondicionado 12, 15 a 20m2 WindFree inverter", "Aire acondicionado 15 a 20m2 Frio-Calor inverter", "Aire Acondocionado split"},
                        // REFRIGERADORES
                         {"Refrigeradora 251L No Frost", "Galanz GLR18FS5S16 Refrigerador de puerta francesa", "Anukis Refrigerador compacto de 2 puertas de 3,1 pies cúbicos", "Refrigerador de puerta francesa de 36 pulgadas"},
                        // ESTEREOS, RADIOS
                        {"Altavoz Blueetooth", "Soundbar Bluetooth Digital 3.0ch", "MiniComponente 300w Xboom CK43", "Minicomponente 450W SC-AKX320", "Minicomponente 5000W CK99 DPERLLK", "Minicomponente Blueetooth XBOOM CL88 2900 watts", "Minicomponente Ok99 1800 watts", "Minicomponente SC-MAX6500PU 4000W", "Torre de sonido 1500w MX-T70"},
                        // LICUADORAS
                        {"Licuadora 1.5L", "Licuadora 1,25L", "Licuadora 1.25L 3 Velocidades"},
                        // ELECTRICO (OLLA ARROCERA, FREIDORA DE AIRE, PARRILLA ELECTRICA)
                        {"Olla Arrocera 2,2L", "Olla Arrocera 1L", "Parrilla eléctrica", "Freidora de Aire 1,9L", "Olla Arrocera Multiusos 1.8L", "Olla Arrocera 1.8L", "Freidora de aire Digital 9L", "Freidora de aire digital Doble 9L"},
                        // ARTEFACTOS DE CUIDADO PERSONAL (SECADORAS, PLANCHAS)
                        {"Plancha", "Cafetera", "Extractor de jugo", "Sandwichera", "Hervidor 1.7L", "Tostador Thomas 2 rebanadas", "Batidora planetaria Pedestal 4,2L", "Exprimidor", "Batidora de mano", "Aspiradora 1,1L", "Wafflera", "Cocedor de huevos"}
                };

                consumos = new double[][]{
                        {0},
                        // PANTALLAS (monitores y televisores)
                        {50, 55, 70, 32, 23, 100, 170, 200, 256, 300},
                        // HORNOS MICROONDAS
                        {1150, 700, 900, 800, 1000, 950, 1500, 1300, 1500, 2600},
                        // LAVADORAS
                        {500, 220, 500, 220, 680, 1880},
                        // COCINAS
                        {2500, 4500, 5000},
                        // VENTILADORES Y AIRE ACONDICIONADO
                        {55,220 , 35, 90, 45, 60, 2500, 1130, 1400, 1790},
                        // REFRIGERADORES
                         {113, 120, 110, 120},
                        // ESTEREOS, RADIOS
                         {80, 400, 399, 450, 5000, 2900, 1800, 4000, 2000, 1500},
                        // LICUADORAS
                         {1000, 700, 700},
                        // ELECTRICO (OLLA ARROCERA, FREIDORA DE AIRE, PARRILLA ELECTRICA)
                        {900, 400, 1500, 1400, 700, 700, 1000, 1700, 1800},
                        // ARTEFACTOS DE CUIDADO PERSONAL (SECADORAS, PLANCHAS)
                        {2200, 1070, 500, 2000, 1850, 800, 800, 25, 320, 1200, 1000, 380}
                };
            }

            public String[] getCategorias() {
                return categorias;
            }

            public String[] getElectrodomesticos(int categoriaIndex) {
                if (categoriaIndex >= 0 && categoriaIndex < electrodomesticos.length) {
                    return electrodomesticos[categoriaIndex];
                }
                return new String[]{};
            }

            public double getConsumoElectrodomestico(int categoriaIndex, int electrodomesticoIndex) {
                if (categoriaIndex >= 0 && categoriaIndex < consumos.length && electrodomesticoIndex >= 0 && electrodomesticoIndex < consumos[categoriaIndex].length) {
                    return consumos[categoriaIndex][electrodomesticoIndex];
                }
                return 0;
            }

            public double getTiempoConsumo(int tiempoIndex) {
                switch (tiempoIndex) {
                    case 1:
                        return 1; // 1 día
                    case 2:
                        return 7; // 7 días
                    case 3:
                        return 30; // 30 días
                    case 4:
                        return 60; // 60 días
                    case 5:
                        return 90; // 90 días
                    case 6:
                        return 120; // 120 días
                    case 7:
                        return 150;  // 150  días
                    case 8:
                        return 180;  // 180  días
                    case 9:
                        return 210;  // 210  días
                    case 10:
                        return 240;  // 240  días
                    case 11:
                        return 270;  // 270  días
                    case 12:
                        return 300;  // 300  días
                    case 13:
                        return 330;  // 330  días
                    case 14:
                        return 365;  // 365  días
                    default:
                        return 0;
                }
            }

        }
    }
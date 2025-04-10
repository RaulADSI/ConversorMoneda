import cachemanager.CacheManager;
import converterlogic.CurrencyConverterLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurrencyConverter extends JFrame implements ActionListener {

    private Container panel;
    private JButton buttonEu, buttonUsd, buttonReset;
    private JTextField mount;

    private CurrencyConverterLogic logic = new CurrencyConverterLogic();

    public static final String CURRENCY_DOLAR = "Dolar";
    public static final String CURRENCY_EURO = "Euro";

    // Declarar cacheManager como una variable de instancia
    private CacheManager cacheManager = new CacheManager();


    public CurrencyConverter() throws HeadlessException {
        super();
        panel = getContentPane();
        panel.setLayout(new FlowLayout());

        mount = new JTextField(10);
        buttonReset = new JButton("Reset");
        buttonReset.addActionListener(this);
        buttonEu = new JButton(CURRENCY_EURO);
        buttonEu.addActionListener(this);
        buttonUsd = new JButton(CURRENCY_DOLAR);
        buttonUsd.addActionListener(this);
        panel.add(mount);
        panel.add(buttonUsd);
        panel.add(buttonEu);
        panel.add(buttonReset);
        setSize(500, 250);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == buttonReset) {
                mount.setText(""); // Limpia el campo de texto
                panel.setBackground(Color.WHITE); // Restaura el color de fondo
                return;
            }

            if (mount.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un valor.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Float value = Float.valueOf(mount.getText());
            String textCurrency = e.getActionCommand();

            // Delegar lógica a CurrencyConverterLogic
            float exchangeRate = logic.getExchangeRate(textCurrency);
            value = value / exchangeRate;

            if (textCurrency.equals(CURRENCY_EURO)) {
                panel.setBackground(Color.YELLOW);
            } else if (textCurrency.equals(CURRENCY_DOLAR)) {
                panel.setBackground(Color.GREEN);
            }

            mount.setText(String.format("%.2f", value));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la API. Verifica tu conexión a internet.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    public static void main(String[] args) {
        new CurrencyConverter();
    }
}

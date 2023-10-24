import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class InvoiceFrame extends JFrame {


    JPanel mainPnl;
    JPanel entryPnl;
    JPanel addressPnl;
    JPanel productInfoPnl;
    JPanel invoicePnl;
    JPanel btnPnl;

    JTextField customerNameTF;
    JTextField streetAddressTF;
    JTextField cityTF;
    JTextField stateTF;
    JTextField zipCodeTF;
    JTextField productNameTF;
    JTextField unitPriceTF;
    JTextField quantityTF;

    JLabel customerNameLbl;
    JLabel streetAddressLbl;
    JLabel cityLbl;
    JLabel stateLbl;
    JLabel zipCodeLbl;
    JLabel productNameLbl;
    JLabel unitPriceLbl;
    JLabel quantityLbl;

    JTextArea invoiceTA;

    JButton quitBtn;
    JButton submitBtn;
    JButton clearBtn;
    JButton addBtn;
    JScrollPane scroller;

    double overallTotal = 0;

    ArrayList<LineItem> lineItems = new ArrayList<>();
    Font invoiceFont = new Font("Monospaced", Font.PLAIN, 12);

    public InvoiceFrame() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        createEntryPnl();
        mainPnl.add(entryPnl, BorderLayout.CENTER);

        createInvoicePnl();
        mainPnl.add(invoicePnl, BorderLayout.EAST);

        createBtnPnl();
        mainPnl.add(btnPnl, BorderLayout.SOUTH);

        add(mainPnl);
        setTitle("Jonathon's Invoice Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(3*(screenWidth / 4), 3*(screenHeight / 4));
        setLocationRelativeTo(null);

    }

    private void createEntryPnl() {
        entryPnl = new JPanel();
        entryPnl.setLayout(new GridLayout(2, 1));

        addressPnl = new JPanel();
        addressPnl.setLayout(new GridLayout(5, 2));
        addressPnl.setBorder(new TitledBorder(new EtchedBorder(), "Enter Customer Information here"));

        productInfoPnl = new JPanel();
        productInfoPnl.setLayout(new GridLayout(3, 2));
        productInfoPnl.setBorder(new TitledBorder(new EtchedBorder(), "Enter Product Information here"));

        customerNameLbl = new JLabel("Customer Name: ");
        streetAddressLbl = new JLabel("Street Address: ");
        cityLbl = new JLabel("City: ");
        stateLbl = new JLabel("State: ");
        zipCodeLbl = new JLabel("Zip Code: ");
        customerNameTF = new JTextField();
        streetAddressTF = new JTextField();
        cityTF = new JTextField();
        stateTF = new JTextField();
        zipCodeTF = new JTextField();

        addressPnl.add(customerNameLbl);
        addressPnl.add(customerNameTF);
        addressPnl.add(streetAddressLbl);
        addressPnl.add(streetAddressTF);
        addressPnl.add(cityLbl);
        addressPnl.add(cityTF);
        addressPnl.add(stateLbl);
        addressPnl.add(stateTF);
        addressPnl.add(zipCodeLbl);
        addressPnl.add(zipCodeTF);

        productNameLbl = new JLabel("Product Name: ");
        unitPriceLbl = new JLabel("Unit Price ($):");
        quantityLbl = new JLabel("Quantity: ");
        productNameTF = new JTextField();
        unitPriceTF = new JTextField();
        quantityTF = new JTextField();

        productInfoPnl.add(productNameLbl);
        productInfoPnl.add(productNameTF);
        productInfoPnl.add(unitPriceLbl);
        productInfoPnl.add(unitPriceTF);
        productInfoPnl.add(quantityLbl);
        productInfoPnl.add(quantityTF);

        entryPnl.add(addressPnl);
        entryPnl.add(productInfoPnl);
    }

    private void createInvoicePnl() {
        invoicePnl = new JPanel();
        invoiceTA = new JTextArea(36, 50);
        invoiceTA.setEditable(false);
        invoiceTA.setFont(invoiceFont);
        scroller = new JScrollPane(invoiceTA);
        invoiceTA.append("                     Invoice                 \n");
        invoiceTA.append("==================================================\n");
        invoiceTA.append(String.format("%-20s%6s%10s%10s", "Item", "Qty", "Price($)", "Total($)") + "\n");
        invoicePnl.add(scroller);
    }

    private void createBtnPnl() {
        btnPnl = new JPanel();
        btnPnl.setLayout(new GridLayout(1, 4));

        addBtn = new JButton("Add Item");
        addBtn.addActionListener((ActionEvent ae) -> addItem(productNameTF.getText(), unitPriceTF.getText(), quantityTF.getText()));
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener((ActionEvent ae) -> submit());
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener((ActionEvent ae) -> clear());
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent ae) -> {
            int choice = JOptionPane.showConfirmDialog(quitBtn, "Do you want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION)
            {
                System.exit(0);}});

        btnPnl.add(addBtn);
        btnPnl.add(submitBtn);
        btnPnl.add(clearBtn);
        btnPnl.add(quitBtn);

    }

    private void addItem(String productName, String unitPrice, String quantity) {
        try {
            double unitPriceConverted = Double.parseDouble(unitPrice);
            try {
                int quantityConverted = Integer.parseInt(quantity);
                LineItem newItem = new LineItem(productName, unitPriceConverted, quantityConverted);
                lineItems.add(newItem);
                invoiceTA.append(newItem.toString());
                productNameTF.setText("");
                unitPriceTF.setText("");
                quantityTF.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Quantity is not in the correct format.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Unit Price is not in the correct format.");
        }
    }

    private void submit() {
        invoiceTA.setText("");
        invoiceTA.append("                     Invoice                 \n");
        invoiceTA.append(" ------------------------------\n");
        invoiceTA.append("|" + String.format("%-30s", customerNameTF.getText()) + "|\n");
        invoiceTA.append("|" + String.format("%-30s", streetAddressTF.getText()) + "|\n");
        invoiceTA.append("|" + String.format("%-30s", cityTF.getText() + ", " + stateTF.getText() + " " + zipCodeTF.getText()) + "|\n");
        invoiceTA.append(" ------------------------------\n");
        invoiceTA.append(String.format("%-20s%6s%10s%10s", "Item", "Qty", "Price($)", "Total($)") + "\n");
        invoiceTA.append("==================================================\n");

        for (LineItem i : lineItems) {
            invoiceTA.append(i.toString());
            overallTotal += i.calculatedTotal;
        }
        invoiceTA.append("==================================================\n");
        invoiceTA.append(String.format("%-5s%.2f","Amount Due: $", overallTotal));

        customerNameTF.setText("");
        streetAddressTF.setText("");
        cityTF.setText("");
        stateTF.setText("");
        zipCodeTF.setText("");
        customerNameTF.setEditable(false);
        streetAddressTF.setEditable(false);
        cityTF.setEditable(false);
        stateTF.setEditable(false);
        zipCodeTF.setEditable(false);
        productNameTF.setEditable(false);
        unitPriceTF.setEditable(false);
        quantityTF.setEditable(false);
    }
    private void clear() {
        invoiceTA.setText("");
        invoiceTA.append("                     Invoice                 \n");
        invoiceTA.append("==================================================\n");
        invoiceTA.append(String.format("%-20s%6s%10s%10s", "Item", "Qty", "Price($)", "Total($)") + "\n");
        customerNameTF.setText("");
        streetAddressTF.setText("");
        cityTF.setText("");
        stateTF.setText("");
        zipCodeTF.setText("");
        productNameTF.setText("");
        unitPriceTF.setText("");
        quantityTF.setText("");
        customerNameTF.setEditable(true);
        streetAddressTF.setEditable(true);
        cityTF.setEditable(true);
        stateTF.setEditable(true);
        zipCodeTF.setEditable(true);
        productNameTF.setEditable(true);
        unitPriceTF.setEditable(true);
        quantityTF.setEditable(true);
        overallTotal = 0;
        lineItems.clear();

    }
}
package com.student.hostel.studenthostelfee;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PrimaryController {
    
    //input fields
    @FXML private TextField nameField;
    @FXML private TextField monthsField;
    @FXML private RadioButton standardRadio;
    @FXML private RadioButton deluxeRadio;
    @FXML private TextField electricityField;
    @FXML private TextField waterField;
    @FXML private CheckBox internetCheckBox;
    @FXML private TextField depositField;
    
    //output labels
    @FXML private Label monthlyLabel;
    @FXML private Label totalLabel;
    @FXML private Label discountLabel;
    @FXML private Label finalLabel;
    @FXML private Label depositResultLabel;
    @FXML private Label messageLabel;
    
    //fixed rates
    final double STANDARD_RATE = 300.00;
    final double DELUXE_RATE = 450.00;
    final double INTERNET_FEE = 50.00;
    final double ELECTRICITY_RATE = 0.25;
    final double DISCOUNT_PERCENT = 0.05;
    final int DISCOUNT_MONTHS = 6;
    
    //calculate fee
    @FXML
    private void handleCalculate() {
        try{
            //read student name
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                messageLabel.setText("ERROR: Please enter student name.");
                return;
            }
            
            //read num of months
            int months = Integer.parseInt(monthsField.getText().trim());
            if (months <=0) {
                messageLabel.setText("ERROR: Months must be more than 0.");
                return;
            }
            
            //read electricity kWh
            double electricity = Double.parseDouble(electricityField.getText().trim());
            if (electricity <0) {
                messageLabel.setText("ERROR: Electricity cannot be negative.");
                return;
            }
            
            //read water charge
            double water = Double.parseDouble(waterField.getText().trim());
            if (water < 0) {
                messageLabel.setText("ERROR: Water cannot be negative.");
                return;
            }
            
            //read security deposit
            double deposit = Double.parseDouble(depositField.getText().trim());
            if (deposit < 0) {
            messageLabel.setText("ERROR: Deposit cannot be negative.");
                return;
            }
        
            //room
            double roomRate;
            if (deluxeRadio.isSelected()) {
                roomRate = DELUXE_RATE;
            } 
            else {
                roomRate = STANDARD_RATE;
            }
            
            //internet
            boolean hasInternet = internetCheckBox.isSelected();
            
            //calculate
            double monthly = calculateMonthlyFee(roomRate, electricity, water, hasInternet);
            double total = monthly * months;
            double discount = calculateDiscount(total,months);
            double finalAmt = calculateFinalPayable(total, discount);
            
            //show results
            monthlyLabel.setText("RM" + String.format("%.2f", monthly));
            totalLabel.setText("RM" + String.format("%.2f", total));
            discountLabel.setText("RM" + String.format("%.2f", discount));
            finalLabel.setText("RM" + String.format("%.2f", finalAmt));
            depositResultLabel.setText("RM" + String.format("%.2f", deposit));
            messageLabel.setText("Calculated successfully for: " + name);
          
            }
        catch (NumberFormatException e) {
            messageLabel.setText("ERROR: Please enter valid numbers in all fields.");
            }
        }

//method 1: calculate monthly fee
private double calculateMonthlyFee (double roomRate, double electricity, double water, boolean hasInternet) {
    double elecCost = electricity * ELECTRICITY_RATE;
    double internetCost = hasInternet? INTERNET_FEE : 0.0;
    return roomRate + elecCost + water + internetCost;
}

//method 2: calculate discount
private double calculateDiscount (double total, int months) {
    if (months > DISCOUNT_MONTHS) {
        return total * DISCOUNT_PERCENT;
    }
    return 0.0;
}

//method 3: calcluate final payable
private double calculateFinalPayable (double total, double discount) {
    return total - discount;
}

@FXML
private void handleClear() {
    nameField.clear();
    monthsField.clear();
    electricityField.clear();
    waterField.clear();
    standardRadio.setSelected(true);
    internetCheckBox.setSelected(false);
    monthlyLabel.setText("RM0.00");
    totalLabel.setText("RM0.00");
    discountLabel.setText("RM0.00");
    depositResultLabel.setText("RM0.00");
    messageLabel.setText("Form cleared.");
}
}
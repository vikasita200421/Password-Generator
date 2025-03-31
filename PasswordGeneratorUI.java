import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

public class PasswordGeneratorUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JTextField passwordLengthField;
    private JCheckBox uppercaseCheckBox;
    private JCheckBox numbersCheckBox;
    private JCheckBox specialCharsCheckBox;
    private JComboBox<String> presetLengthComboBox;
    private JButton generateButton;

    public PasswordGeneratorUI() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Random Password Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        createComponents();
        addComponentsToPanel();

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createComponents() {
        passwordLengthField = new JTextField(5);
        passwordLengthField.setToolTipText("Enter desired password length");

        uppercaseCheckBox = new JCheckBox("Include Uppercase Letters");
        numbersCheckBox = new JCheckBox("Include Numbers");
        specialCharsCheckBox = new JCheckBox("Include Special Characters");

        String[] presetLengths = {"8", "12", "16", "20", "24"};
        presetLengthComboBox = new JComboBox<>(presetLengths);
        presetLengthComboBox.setToolTipText("Select a common password length");

        generateButton = new JButton("Generate Password");
        generateButton.setBackground(new Color(0, 150, 0));
        generateButton.setForeground(Color.WHITE);
        generateButton.setToolTipText("Generate your random password");

        // Add event listener for password generation
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePassword();
            }
        });
    }

    private void addComponentsToPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(new JLabel("Password Generator"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Password Length:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(passwordLengthField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Preset Length:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(presetLengthComboBox, gbc);

        JPanel checkboxesPanel = new JPanel();
        checkboxesPanel.setLayout(new BoxLayout(checkboxesPanel, BoxLayout.Y_AXIS));
        checkboxesPanel.setBorder(BorderFactory.createTitledBorder("Character Types"));

        checkboxesPanel.add(uppercaseCheckBox);
        checkboxesPanel.add(numbersCheckBox);
        checkboxesPanel.add(specialCharsCheckBox);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(checkboxesPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(generateButton, gbc);
    }

    private void generatePassword() {
        int length;

        // Get length from user input or preset dropdown
        try {
            if (!passwordLengthField.getText().isEmpty()) {
                length = Integer.parseInt(passwordLengthField.getText());
            } else {
                length = Integer.parseInt((String) presetLengthComboBox.getSelectedItem());
            }

            if (length <= 0) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid password length!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input! Enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Define character sets
        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numberChars = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[]{}|;:,.<>?/";

        // Build the character pool based on user selection
        StringBuilder characterPool = new StringBuilder(lowercaseChars); // Default: lowercase included

        if (uppercaseCheckBox.isSelected()) {
            characterPool.append(uppercaseChars);
        }
        if (numbersCheckBox.isSelected()) {
            characterPool.append(numberChars);
        }
        if (specialCharsCheckBox.isSelected()) {
            characterPool.append(specialChars);
        }

        if (characterPool.length() == 0) {
            JOptionPane.showMessageDialog(frame, "Please select at least one character type!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Generate password
        String password = generateRandomPassword(length, characterPool.toString());

        // Show password in a message box
        JOptionPane.showMessageDialog(frame, "Generated Password: " + password, "Password", JOptionPane.INFORMATION_MESSAGE);
    }

    private String generateRandomPassword(int length, String characterPool) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterPool.length());
            password.append(characterPool.charAt(index));
        }

        return password.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PasswordGeneratorUI::new);
    }
}

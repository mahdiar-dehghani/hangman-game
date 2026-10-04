import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Hangman extends JFrame {

    // In here I wanna give the user 6 chances to find the word ;)
    private static final int maximum_wrong_guesses = 6;


    //These are gonna be the instance variables to use in our classes.

    private final HangmanPanel hangmanPanel;
    private JLabel word_label1;
    private JLabel wrong_letters_label1;
    private JLabel attempts_label1;
    private JLabel status_label1;
    private JTextField guess_fields1;
    private JButton guess_button1;
    private JButton restarting_game1;

    // These are the memory & logic of our game

    private final List<String> all_words;
    private final Random random1;
    private final Set<Character> letter_guess1;
    private final Set<Character> incorrect_words1;

    private String unknown_word1;
    private boolean gaming_over;
    private int incorrect_guesses1;
    private boolean win = false;

    // In here I tried to choose some cool color to make the Game beautiful ;)

    private static final Color left_color_panel = new Color(255, 253, 200); // Yellow
    private static final Color right_color_panel = new Color(200, 255, 200); // Blue
    private static final Color header_color = new Color(255, 200, 200); // Header color


    // In here I started to set up the constructor
    public Hangman(){
        super("***************************************************** Hangman Game ***************************************************************");

        // In here I make my word bank and make the code to choose one of the words randomly.

        random1 = new Random();
        letter_guess1 = new HashSet<>();
        incorrect_words1 = new HashSet<>();
        all_words = Arrays.asList("JAVA", "COMPUTER", "GRAPHICS", "WINDOW", "PROJECT", "SWING", "SCHOOL", "ANGEL");

        // In here I wanted to make sure everything is organized and centered.
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);

        // this is gonna write a warm welcome at the top of the game.

        JPanel make_header1 = new JPanel();
        make_header1.setBackground(header_color);
        make_header1.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel game_header_title1 = new JLabel("WELCOME TO HANGMAN!");
        game_header_title1.setFont(new Font("Arial", Font.BOLD, 28));
        game_header_title1.setForeground(Color.WHITE);
        make_header1.add(game_header_title1);
        add(make_header1, BorderLayout.NORTH);

        // This is the center part where is the 2D drawing works

        hangmanPanel = new HangmanPanel();
        add(hangmanPanel, BorderLayout.CENTER);

        // This is the right part of the program where user plays.
        add(panel_right(), BorderLayout.EAST);



        //this part is connecitng the listeners & starting the first game

        attatching_listeners();
        making_new_game();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel panel_right() {
        //The purpose of this part is to make interaction panel on the right.
        // I tired to use (GridBagLayout) to make everything to stay perfectly centered


        JPanel panel_right1 = new JPanel(new GridBagLayout()); 
        panel_right1.setPreferredSize(new Dimension(450, 0));
        panel_right1.setBackground(right_color_panel);
        panel_right1.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.LIGHT_GRAY));

        JPanel content_area1 = new JPanel();
        content_area1.setLayout(new BoxLayout(content_area1, BoxLayout.Y_AXIS));
        content_area1.setOpaque(false);

        // This part purpose is to show the word choosen in a large and good font to player.

        word_label1 = new JLabel("_ _ _ _", SwingConstants.CENTER);
        word_label1.setFont(new Font("Monospaced", Font.BOLD, 45));
        word_label1.setAlignmentX(Component.CENTER_ALIGNMENT);


        //This is the guess box, The thing that I did here is if the user press the TAB key, the game will restart immediately.
        guess_fields1 = new JTextField(1);
        guess_fields1.setMaximumSize(new Dimension(100, 60));
        guess_fields1.setFont(new Font("Arial", Font.BOLD, 32));
        guess_fields1.setHorizontalAlignment(JTextField.CENTER);
        guess_fields1.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        guess_fields1.setFocusTraversalKeysEnabled(false);

        // In here i change the buttons color to make them beautiful

        guess_button1 = make_style_button("TAKE A GUESS", new Color(52, 152, 219));
        restarting_game1 = make_style_button("PLAY AGAIN", new Color(46, 204, 113));

        wrong_letters_label1 = new JLabel("Missed: -");
        attempts_label1 = new JLabel("Lives: 6");
        status_label1 = new JLabel("Start Guessing!");

        side_side_label(wrong_letters_label1, 20, new Color(192, 57, 43));
        side_side_label(attempts_label1, 22, Color.DARK_GRAY);
        side_side_label(status_label1, 26, new Color(44, 62, 80));


        // In this part, the code will tidying up all the components vertically.

        content_area1.add(word_label1);
        content_area1.add(Box.createRigidArea(new Dimension(0, 40)));
        content_area1.add(new JLabel("Type Your Letter plz:") {{ setAlignmentX(CENTER_ALIGNMENT); }});
        content_area1.add(Box.createRigidArea(new Dimension(0, 10)));
        content_area1.add(guess_fields1);
        content_area1.add(Box.createRigidArea(new Dimension(0, 20)));
        content_area1.add(guess_button1);
        content_area1.add(Box.createRigidArea(new Dimension(0, 10)));
        content_area1.add(restarting_game1);
        content_area1.add(Box.createRigidArea(new Dimension(0, 40)));
        content_area1.add(wrong_letters_label1);
        content_area1.add(Box.createRigidArea(new Dimension(0, 10)));
        content_area1.add(attempts_label1);
        content_area1.add(Box.createRigidArea(new Dimension(0, 40)));
        content_area1.add(status_label1);

        panel_right1.add(content_area1, new GridBagConstraints());
        return panel_right1;
    }

    private JButton make_style_button(String text, Color bg) {
        JButton make_button1 = new JButton(text);
        make_button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        make_button1.setMaximumSize(new Dimension(220, 50));
        make_button1.setFont(new Font("Arial", Font.BOLD, 16));
        make_button1.setBackground(bg);
        make_button1.setForeground(Color.WHITE);
        make_button1.setFocusPainted(false);
        make_button1.setBorderPainted(false);
        make_button1.setOpaque(true);
        make_button1.setCursor(new Cursor(Cursor.HAND_CURSOR));


        return make_button1;
    }



    private void side_side_label(JLabel label1, int size1, Color color1) {
        label1.setFont(new Font("Arial", Font.BOLD, size1));
        label1.setForeground(color1);
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // THis is actually the main part of the code, In here it will
    // listen to all the button clicks and also the TAB key to make
    // the game goes on and works properly.

    private void attatching_listeners(){

        ActionListener guessing_action1 = e -> {
            if (gaming_over) return;
            String take1 = guess_fields1.getText().trim();
            if (!take1.isEmpty()) checking_guesses(take1.toUpperCase().charAt(0));
            guess_fields1.setText("");
            guess_fields1.requestFocus();
        };
        guess_button1.addActionListener(guessing_action1);
        guess_fields1.addActionListener(guessing_action1);
        restarting_game1.addActionListener(e -> making_new_game());

        // this will reset the game immediately if the TAB being pressed!.

        guess_fields1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent element1) {
                if (element1.getKeyCode() == KeyEvent.VK_TAB) {
                    making_new_game();
                }
            }
        });

    }


    //This section would easily again picks a new randomly word and tells the graphics to clear up the screen.

    private void making_new_game() {
        unknown_word1 = all_words.get(random1.nextInt(all_words.size())).toUpperCase();
        letter_guess1.clear();
        incorrect_words1.clear();
        incorrect_guesses1 = 0;
        gaming_over = false;
        win = false;
        updating_words();
        updating_information();
        status_label1.setText("ENJOY! Press TAB To Reset!");
        status_label1.setForeground(Color.GRAY);
        hangmanPanel.repaint();

    }

    //This is actually the main brain of the program
    //it will check if the player finds a letter.
    // Each mistake by the player will sketch and complete our hangman drawing ;)

    private void checking_guesses(char character2){
        if (gaming_over || letter_guess1.contains(character2)) return;
        letter_guess1.add(character2);

        if (unknown_word1.indexOf(character2) == -1) {
            incorrect_words1.add(character2);
            incorrect_guesses1++;
            status_label1.setText("Wrong!");
        }else {
            status_label1.setText("Correct!");
        }
        updating_words();
        updating_information();
        game_checking();
        hangmanPanel.repaint();
    }

    private void updating_words() {
        StringBuilder word_sb1 = new StringBuilder();
        for (char character1 : unknown_word1.toCharArray()) {
            word_sb1.append(letter_guess1.contains(character1) ? character1 : "_").append(" ");
        }
        word_label1.setText(word_sb1.toString().trim());
    }

    private void updating_information() {
        wrong_letters_label1.setText("Incorrect Words: " + incorrect_words1.toString());
        attempts_label1.setText("Lives: " + (maximum_wrong_guesses - incorrect_guesses1));
    }


    // This part would check how many more quesses the user have

    private void game_checking() {
        if (!word_label1.getText().contains("_")) {
            gaming_over = true;
            win = true;
        } else if (incorrect_guesses1 >= maximum_wrong_guesses) {
            gaming_over = true;
            win = false;
            word_label1.setText(unknown_word1);
        }
    }


    // This is the most beautiful part of the code, In here I used the 2D graphics to draw
    // and sketch the hangman (gallows) and it will being completed based on the player mistakes.


    private class HangmanPanel extends JPanel {
        protected void paintComponent(Graphics graphics_1) {
            super.paintComponent(graphics_1);
            Graphics2D graphics_2 = (Graphics2D) graphics_1;
            graphics_2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // This would make the left panel color to beautiful yellow color

            graphics_2.setPaint(new GradientPaint(0, 0, left_color_panel, 0, getHeight(), Color.WHITE));
            graphics_2.fillRect(0, 0, getWidth(), getHeight());

            // In this part I used my own creativity ;)
            // if the user wins, the hangman will turn green
            //and if the user fails, the hangman will turn to red


            if (gaming_over) {
                graphics_2.setColor(win ? new Color(39, 174, 96) : new Color(192, 57, 43));
            } else {
                graphics_2.setColor(new Color(60, 40, 20));
            }

            graphics_2.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            //Drawing the gallows parts.

            int x = 150;
            graphics_2.drawLine(x - 50, 550, x + 150, 550); 
            graphics_2.drawLine(x, 550, x, 150); 
            graphics_2.drawLine(x, 150, x + 150, 150); 
            graphics_2.drawLine(x + 150, 150, x + 150, 200); 

            if (incorrect_guesses1 > 0) graphics_2.drawOval(x + 125, 200, 50, 50); 
            if (incorrect_guesses1 > 1) graphics_2.drawLine(x + 150, 250, x + 150, 380); 
            if (incorrect_guesses1 > 2) graphics_2.drawLine(x + 150, 280, x + 110, 330); 
            if (incorrect_guesses1 > 3) graphics_2.drawLine(x + 150, 280, x + 190, 330); 
            if (incorrect_guesses1 > 4) graphics_2.drawLine(x + 150, 380, x + 110, 450); 
            if (incorrect_guesses1 > 5) graphics_2.drawLine(x + 150, 380, x + 190, 450); 

            // This will print the win or lose message when the user wins or fails.

            if (gaming_over) {
                String resultText = win ? "YOU WON!" : "YOU LOST!";
                graphics_2.setFont(new Font("Arial", Font.BOLD, 85));
                
                // This is the soft shadow to make the text pop

                graphics_2.setColor(new Color(0, 0, 0, 40));
                graphics_2.drawString(resultText, 104, 354);

                graphics_2.setColor(win ? new Color(39, 174, 96) : new Color(192, 57, 43));
                graphics_2.drawString(resultText, 100, 350);
            }
        }
    }

    // THis is the starting points where our hangman game starts

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Hangman::new);
    }
}
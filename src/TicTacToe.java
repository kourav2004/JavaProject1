import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class TicTacToe {

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JLabel scoreLabel = new JLabel(); // Score display label
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel controlPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    JButton resetButton = new JButton("Reset");
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;

    // Scores
    int scoreX = 0;
    int scoreO = 0;
    int ties = 0;


    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);

        // Score label
        scoreLabel.setForeground(Color.blue);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);

        textPanel.setLayout(new GridLayout(2, 1)); // Two rows
        textPanel.add(textLabel);
        textPanel.add(scoreLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // Reset Button
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(resetButton);

        frame.add(textPanel, BorderLayout.NORTH);
        frame.add(controlPanel, BorderLayout.SOUTH);


        frame.add(textPanel,BorderLayout.NORTH);
        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);
        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial",Font.BOLD,120));
                tile.setFocusable(false);
                

                tile.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(gameOver) return;
                        JButton tile = (JButton) e.getSource();

                        if(tile.getText().equals("")){
                             tile.setText(currentPlayer);
                             turns++;
                             checkWinner();
                             if(!gameOver){
                        //         currentPlayer = currentPlayer == playerX ? playerO : playerX;
                        // textLabel.setText(currentPlayer + "'S turn.");
                        currentPlayer = playerO;
                        textLabel.setText("Computer's turn...");

                        Timer timer = new Timer(500, new ActionListener() {
                                    public void actionPerformed(ActionEvent evt) {
                                        computerMove();
                                    }
                                });
                                timer.setRepeats(false);
                                timer.start();

                             }

                        

                        }
                       


                    }
                });
            }
        }
        // Reset button listener
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        updateScoreLabel();
    }
    // ai based
    void computerMove() {
        if (gameOver) return;

        ArrayList<JButton> emptyTiles = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].getText().equals("")) {
                    emptyTiles.add(board[r][c]);
                }
            }
        }

        if (!emptyTiles.isEmpty()) {
            JButton move = emptyTiles.get(new Random().nextInt(emptyTiles.size()));
            move.setText(playerO);
            turns++;
            checkWinner();
            if (!gameOver) {
                currentPlayer = playerX;
                textLabel.setText(currentPlayer + "'S turn.");
            }
        }
    }
    // over

    
    void checkWinner(){
        for(int r=0;r<3;r++){
            if(board[r][0].getText() == "") continue;

            if(board[r][0].getText().equals(board[r][1].getText()) && board[r][1].getText().equals(board[r][2].getText())){
                for(int i=0;i<3;i++){
                    setWinner(board[r][i]);
                }
                // gameOver = true;
                // return;
                updateScores();
                return;
            }
        }
        for(int c=0;c<3;c++){
            if(board[0][c].getText() == "") continue;
            if(board[0][c].getText().equals(board[1][c].getText()) && board[1][c].getText().equals(board[2][c].getText())){
                for(int i=0;i<3;i++){
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }
        if(board[0][0].getText().equals(board[1][1].getText()) && board[1][1].getText().equals(board[2][2].getText()) && board[0][0].getText() != ""){
            for(int i=0;i<3;i++){
                setWinner(board[i][i]);

            }
            gameOver = true;
            return;
        }
        if(board[0][2].getText().equals(board[1][1].getText()) && board[1][1].getText().equals(board[2][0].getText()) && board[0][2].getText() != ""){
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;

        }
        if(turns == 9){
            for(int r=0;r<3;r++){
                for(int c=0;c<3;c++){
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }
    void setWinner(JButton tile){
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner!");
    }

    void updateScores() {
        if (currentPlayer.equals(playerX)) {
            scoreX++;
        } else {
            scoreO++;
        }
        updateScoreLabel();
        gameOver = true;
    }

    void setTie(JButton tile){
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");
    }
    
    void updateScoreLabel() {
        scoreLabel.setText("Score - X: " + scoreX + "   O: " + scoreO + "   Ties: " + ties);
    }

    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setForeground(Color.white);
                board[r][c].setBackground(Color.darkGray);
            }
        }
        turns = 0;
        gameOver = false;
        currentPlayer = playerX;
        textLabel.setText("Tic-Tac-Toe");
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
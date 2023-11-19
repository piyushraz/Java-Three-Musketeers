package assignment1;

import java.util.Scanner;


public class ThreeMusketeers {

    private Board board;
    private final MovesRepository movesRepository;
    private final BestMove bestMove;
    private Agent musketeerAgent, guardAgent;
    private final Scanner scanner = new Scanner(System.in);
    // All possible game modes
    public enum GameMode {
        Human("Human vs Human"),
        HumanRandom("Human vs Computer (Random)"),
        HumanGreedy("Human vs Computer (Greedy)");

        private final String gameMode;
        GameMode(final String gameMode) {
            this.gameMode = gameMode;
        }
    }

    /**
     * Default constructor to load Starter board
     */
    public ThreeMusketeers() {
    	this.board = new Board();
		this.movesRepository = new MovesRepository();
		this.movesRepository.createText("MoveLog");
		this.bestMove = new BestMove(board);
    }
    
    public Board getBoard() {
    	return board;
    }

    /**
     * Constructor to load custom board
     * @param boardFilePath filepath of custom board
     */
    public ThreeMusketeers(String boardFilePath) {
        board = new Board();
		this.movesRepository = new MovesRepository();
		this.movesRepository.createText("MoveLog");
		this.bestMove = new BestMove(board);
    }

    /**
     * Play game with human input mode selector
     */
    public void play(){
        System.out.println("Welcome! \n");
        final GameMode mode = getModeInput();
        System.out.println("Playing " + mode.gameMode);
        play(mode);
    }

    /**
     * Play game without human input mode selector
     * @param mode the GameMode to run
     */
    public void play(GameMode mode){
    	// this gets character input after setting mode
    	
        selectMode(mode);
    	
    	getCharacterInput();
        runGame();
    }

    /**
     * Mode selector sets the correct agents based on the given GameMode
     * @param mode the selected GameMode
     */
    private void selectMode(GameMode mode) {
        switch (mode) {
            case Human -> {
                musketeerAgent = new HumanAgent(board);
                guardAgent = new HumanAgent(board);
            }
            case HumanRandom -> {
                String side = getSideInput();
                
                // The following statement may look weird, but it's what is known as a ternary statement.
                // Essentially, it sets musketeerAgent equal to a new HumanAgent if the value M is entered,
                // Otherwise, it sets musketeerAgent equal to a new RandomAgent
                musketeerAgent = side.equals("M") ? new HumanAgent(board) : new RandomAgent(board);
                
                guardAgent = side.equals("G") ? new HumanAgent(board) : new RandomAgent(board);
            }
            case HumanGreedy -> {
                String side = getSideInput();
                musketeerAgent = side.equals("M") ? new HumanAgent(board) : new GreedyAgent(board);
                guardAgent = side.equals("G") ? new HumanAgent(board) : new GreedyAgent(board);
            }
        }
    }

    /**
     * Runs the game, handling human input for move actions
     * Handles moves for different agents based on current turn 
     */
    private void runGame() {
    	  boolean flag = false;
        while(!board.isGameOver() && flag == false) {
            System.out.println("\n" + board);
            final Agent currentAgent;
            if (board.getTurn() == Piece.Type.MUSKETEER)
                currentAgent = musketeerAgent;
            else
                currentAgent = guardAgent;

            if (currentAgent instanceof HumanAgent) // Human move
                switch (getInputOption()) {
                    case "M":
                        move(currentAgent);
                        break;
                    case "U":
                        if (movesRepository.size() == 0) {
                            System.out.println("No moves to undo.");
                            continue;
                        }
                        else if (movesRepository.size() == 1 || isHumansPlaying()) {
                            undoMove();
                        }
                        else {
                            undoMove();
                            undoMove();
                        }
                        break;
                    case "E":
                    	flag = true;
                      break;
                    
                    case "T":
                    	if (movesRepository.size() != 0) {
                        	board.changeTurn();
                    	}
                    	break;
                    case "H":
                    	System.out.println(bestMove.randomMove());
                    	break;
                    case "S":
                        board.saveBoard();
                        break;
                    case "R": 
                    	// randomize the board 
                    	Board oldBoard = getBoard();
                    	oldBoard.randomizeBoard(oldBoard);
                    	this.movesRepository.randomizedboard();
                    	break;
                    case "P":{
                    	moveIterator it = movesRepository.iterator();
                    	if(board.getTurn() == Piece.Type.MUSKETEER) {
                    		String userin = "P";
                    		while(userin.equals("P") && it.musketeerHasPrev()) {
                    			System.out.println(it.musketeerPrev());
                    			System.out.println("Enter 'P' to print previous move, or 'B' to go back");
                    			userin = scanner.next();
                    			while (!(userin.equalsIgnoreCase("P")) && !(userin.equalsIgnoreCase("B"))) {
                    	            System.out.print("Invalid option. Enter 'P' or 'B': ");
                    	            userin = scanner.next();
                    	        }
                    		}
                    		if(!(it.musketeerHasPrev())) {
                    			System.out.println("Sorry, no more moves to print");
                    			break;
                    		}
                    		break;
                    		
                    	}
                    	if(board.getTurn() == Piece.Type.GUARD) {
                    		String userin = "P";
                    		while(userin.equals("P") && it.guardHasPrev()) {
                    			System.out.println(it.guardPrev());
                    			System.out.println("Enter 'P' to print previous move, or 'B' to go back");
                    			userin = scanner.next();
                    			while (!(userin.equalsIgnoreCase("P")) && !(userin.equalsIgnoreCase("B"))) {
                    	            System.out.print("Invalid option. Enter 'P' or 'B': ");
                    	            userin = scanner.next();
                    	        }
                    		}
                    		if(!(it.guardHasPrev())) {
                    			System.out.println("Sorry, no more moves to print");
                    			break;
                    		}
                    		break;
                    	}
                    }
                    	
                }
            else { // Computer move
                System.out.printf("[%s] Calculating move...\n", currentAgent.getClass().getSimpleName());
                move(currentAgent);
            }
        }

        System.out.println(board);
        if (flag) {
        	System.out.println("No winner.");
        }
        else {
        System.out.printf("\n%s won!%n", board.getWinner().getType());
        }

    }

    /**
     * Gets a move from the given agent, adds a copy of the move using the copy constructor to the moves stack, and
     * does the move on the board.
     * @param agent Agent to get the move from.
     */
    protected void move(final Agent agent) {
        final Move move = agent.getMove();
//        moves.add(new Move(move));
        movesRepository.addMove(new Move(move), board.getTurn());
        board.move(move);
    }

    /**
     * Removes a move from the top of the moves stack and undoes the move on the board.
     */
    private void undoMove() {
        board.undoMove(movesRepository.removeMove());
        System.out.println("Undid the previous move.");
    }

    /**
     * Get human input for move action
     * @return the selected move action, 'M': move, 'U': undo, and 'S': save
     */
    private String getInputOption() {
    	System.out.printf("%47s\n Enter:\n", "[" + board.getTurn().getType() + "'S TURN]");
    	System.out.printf("%40s\n", "--------------------------------------------------------------------------");
    	System.out.printf("%40s\n", "|'M' to move          |'H' for a hint               |'S' to save:        |");
    	System.out.printf("%40s\n", "|'U' to undo          |'R' to randomize             |'E' to end game     |");
    	System.out.printf("%40s\n", "|'T' to skip turn     |'P' for previous move(s)     |                    |");
    	System.out.printf("%40s\n", "--------------------------------------------------------------------------");
        while (!scanner.hasNext("[MUTHSRPEmuthsrpe]")) {
            System.out.print("Invalid option. Enter 'M', 'U', 'T', 'H', 'S', 'R', 'E', or 'P': ");
            scanner.next();
        }
        return scanner.next().toUpperCase();
    }
    

    /**
     * Returns whether both sides are human players
     * @return True if both sides are Human, False if one of the sides is a computer
     */
    private boolean isHumansPlaying() {
        return musketeerAgent instanceof HumanAgent && guardAgent instanceof HumanAgent;
    }

    /**
     * Get human input for side selection
     * @return the selected Human side for Human vs Computer games,  'M': Musketeer, G': Guard
     */
    private String getSideInput() {
        System.out.print("Enter 'M' to be a Musketeer or 'G' to be a Guard: ");
        while (!scanner.hasNext("[MGmg]")) {
            System.out.println("Invalid option. Enter 'M' or 'G': ");
            scanner.next();
        }
        return scanner.next().toUpperCase();
    }

    /**
     * Get human input for selecting the game mode
     * @return the chosen GameMode
     */
    private GameMode getModeInput() {
        System.out.println("""
                    0: Human vs Human
                    1: Human vs Computer (Random)
                    2: Human vs Computer (Greedy)""");
        System.out.print("Choose a game mode to play i.e. enter a number: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid option. Enter 0, 1, or 2: ");
            scanner.next();
        }
        final int mode = scanner.nextInt();
        if (mode < 0 || mode > 2) {
            System.out.println("Invalid option.");
            return getModeInput();
        }
        return GameMode.values()[mode];
    }
    
    /**
     * Get the player character input
     */
    private void getCharacterInput() {
    	// have to change pieces of cells based on input 
    	// have to change loaded board 
    	   	
    	// this checks that the chosen character for Muskeeter is 
    	// alphanumeric and stores it in musketeerChar
    	
        System.out.println("-------------------------------\n" +
        "Choose your Muskeeter character: ");
        String userInput = scanner.next();
        while (!userInput.matches("[A-Za-z0-9]")) {
            System.out.print("Choose a single alphanumeric character: ");
            userInput = scanner.next();
        }
        
        String musketeerChar = userInput;
        Musketeer.setSymbol(musketeerChar);
        
    	// this checks that the chosen character for Guard is 
    	// alphanumeric + that it is not the same as the Muskeeter character
        // finally, it will store it in guardChar
    	
        System.out.println("Choose your Guard character: ");
        String userInput2 = scanner.next();
        String input2 = userInput2;
        
        while (!input2.matches("[A-Za-z0-9]") || input2.equals(userInput)) {
        	if (!input2.matches("[A-Za-z0-9]")) {
        		System.out.print("Choose a single alphanumeric character: ");
        		}
        	
        	else if (input2.equals(userInput)) {
        		System.out.print("Choose a different character from Muskeeter: ");	
        	} 
        	
            userInput2 = scanner.next();
            input2 = userInput2;
            
        }
        String guardChar = userInput2;
        Guard.setSymbol(guardChar);
   
        String boardFileName = "Boards/Starter.txt";
        getBoard().loadBoard(boardFileName);
    	
    }
    

    

    public static void main(String[] args) {
        String boardFileName = "Boards/Starter.txt";
        ThreeMusketeers game = new ThreeMusketeers(boardFileName);
        game.play();
    }
}

package assignment1;

public interface Agent {
    
    /**
     * An Agent that can play ThreeMusketeers
     * @param board a Board the Agent can play on
     */
     /**
     * Gets a valid move that the Agent can perform on the Board
     * @return a valid Move that the Agent can do
     */
    public abstract Move getMove();
}

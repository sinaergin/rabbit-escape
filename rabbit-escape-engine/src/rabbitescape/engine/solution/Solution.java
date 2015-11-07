package rabbitescape.engine.solution;

import java.util.Arrays;
import java.util.List;

import rabbitescape.engine.util.Util;

public class Solution
{
    public final int solutionId;
    public final List<Instruction> instructions;

    public Solution( int solutionId, List<Instruction> instructions )
    {
        this.solutionId = solutionId;
        this.instructions = instructions;
    }

    public String relFormat()
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for ( Instruction instruction : instructions )
        {
            if (!first)
            {
                sb.append( SolutionFactory.INSTRUCTION_DELIMITER );
            }
            sb.append( instruction.relFormat() );
            first = false;
        }
        return sb.toString();
    }

    @Override
    public String toString()
    {
        return "Solution( "
            + solutionId
            + ", [ " + Util.join( ", ", instructions )
            + " ] )";
    }

    @Override
    public boolean equals( Object other )
    {
        if ( ! ( other instanceof Solution ) )
        {
            return false;
        }
        Solution otherSolution = (Solution)other;

        return (
            solutionId == otherSolution.solutionId
            &&
            Arrays.deepEquals(
                instructionsArray(), otherSolution.instructionsArray() )
        );
    }

    private Instruction[] instructionsArray()
    {
        return instructions.toArray( new Instruction[ instructions.size() ] );
    }

    @Override
    public int hashCode()
    {
        return solutionId + Arrays.deepHashCode( instructionsArray() );
    }
}

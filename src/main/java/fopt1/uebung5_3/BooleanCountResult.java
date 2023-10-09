package fopt1.uebung5_3;

public class BooleanCountResult {

    private final int result;

    private final int depth;

    private final int nodeIndex;

    public BooleanCountResult(int result) {
        this(result, 1, 1);
    }

    public BooleanCountResult(int result, int depth, int nodeIndex) {
        this.result = result;
        this.depth = depth;
        this.nodeIndex = nodeIndex;
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public int getResult() {
        return result;
    }

    public int getDepth() {
        return depth;
    }

    public String toString() {
        return "result: " + this.result + "; depth: " + depth + "; nodeIndex; " + nodeIndex;
    }
}

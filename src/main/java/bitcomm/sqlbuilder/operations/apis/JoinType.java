package bitcomm.sqlbuilder.operations.apis;

public class JoinType {

    private String type;
    public JoinType() {
	type=INNER;
    }
    public void setType(String type) {
	this.type=type;
    }
    public String getName() {
	return type;
    }
    public final static String INNER = "INNER JOIN";
    public final static  String FULL_OUTTER = "FULL OUTER JOIN";
    public final static  String RIGHT = "RIGHT JOIN";
    public final static String LEFT = "LEFT JOIN";
}

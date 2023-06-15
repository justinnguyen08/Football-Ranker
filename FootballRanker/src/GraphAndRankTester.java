/* CS 314 STUDENTS: FILL IN THIS HEADER.
 *
 * Student information for assignment:
 *
 *  On my honor, Justin Nguyen, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: jn28429
 *  email address: justinguyen08@gmail.com
 *  TA name: Trisha
 */

/*
 * Questions.
 *
 * 1. The assignment presents three ways to rank teams using graphs.
 * The results, especially for the last two methods are reasonable.
 * However if all results from all college football teams are included
 * some unexpected results occur. Explain the unexpected results. You may
 * have to do some research on the various college football divisions to
 * make an informed answer.
 *
 * Some teams are placed in specific football divisions where they'll always go against
 * all the other teams in the same division. Other teams are not placed in a division,
 * so they are usually put against a various amount of teams. Some teams are going to be
 * more difficult to rank against other teams because of this instance.
 *
 * 2. Suggest another way of method of ranking teams using the results
 * from the graph. Thoroughly explain your method. The method can build
 * on one of the three existing algorithms.
 * 
 * If we were able to look deeper into the more advannced statistics, we could find 
 * other ways to rank the teams. For example, one of these stats could be the strength of 
 * a team's schedule. If we compare these strengths (or other advance stats) to other teams, 
 * there could be a way to additionally base our rankings based off these advanced stats.
 */

public class GraphAndRankTester {

    /**
     * Runs tests on Graph classes and FootballRanker class.
     * Experiments involve results from college football
     * teams. Central nodes in the graph are compared to
     * human rankings of the teams.
     * @param args None expected.
     */
    public static void main(String[] args)  {
    	studentTests();
    }

    private static void studentTests() {
    	// test 1
    	String [][] g1Edges = {{"A", "D", "2"},
                {"B", "A", "3"},
                {"B", "C", "9"},
                {"D", "C", "3"},
                {"C", "A", "5"},
                {"D", "E", "2"}};
    	Graph g1 = getGraph(g1Edges, true);
    	g1.dijkstra("A");
    	String actualPath = g1.findPath("E").toString();
    	String expected = "[A, D, E]";
    	if (actualPath.equals(expected)) {
    		System.out.println("Test 1: dijkstra passed");
    	} else {
    		System.out.println("Test 1: dijkstra FAILED");
    	}
    	
    	// test 2
    	g1.dijkstra("B");
    	actualPath = g1.findPath("D").toString();
    	expected = "[B, A, D]";
    	if (actualPath.equals(expected)) {
    		System.out.println("Test 2: dijkstra passed");
    	} else {
    		System.out.println("Test 2: dijsktra FAILED");
    	}
    	
        String[][] g2Edges = {{"E", "G", "4.3"},
                        {"G", "F", "8.1"},
                        {"D", "F", "4.2"},
                        {"B", "A", "4.6"},
                        {"E", "B", "9.3"},
                        {"B", "E", "12.0"},
                        {"F", "A", "5.4"},
                        {"E", "F", "19.0"},
                        {"F", "C", "4.4"},
                        {"C", "F", "7.0"},
                        {"A", "E", "9.9"},
                        {"E", "C", "12.8"},
                        {"A", "G", "8.0"},
                        {"F", "E", "1.8"}};
        Graph g2 = getGraph(g2Edges, false);
        
        // test 3
        System.out.println("Test 3: findAllPaths");
        doAllPathsTests("Graph 2", g2, false, 3, 3.0);
        
        // test 4
        System.out.println("Test 4: findAllPaths");
        doAllPathsTests("Graph 2", g2, true, 3, 17.0);
        
        // test 5, 6 & 7 rankTests
        String actual = "2014ap_poll.txt";
        String gameResults = "div12014.txt";
        FootballRanker ranker = new FootballRanker(gameResults, actual);
        doRankTests(ranker);
    }
   
    // return a Graph based on the given edges
    private static Graph getGraph(String[][] edges, boolean directed) {
        Graph result = new Graph();
        for (String[] edge : edges) {
            result.addEdge(edge[0], edge[1], Double.parseDouble(edge[2]));
            // If edges are for an undirected graph add edge in other direction too.
            if (!directed) {
                result.addEdge(edge[1], edge[0], Double.parseDouble(edge[2]));
            }
        }
        return result;
    }

    // Tests the all paths method. Run each set of tests twice to ensure the Graph
    // is correctly reseting each time
    private static void doAllPathsTests(String graphNumber, Graph g, boolean weighted,
                    int expectedDiameter, double expectedCostOfLongestShortestPath) {
        System.out.println("\nTESTING ALL PATHS INFO ON " + graphNumber + ". ");
        System.out.println("Find all paths weighted = " + weighted);
        g.findAllPaths(weighted);
        int actualDiameter = g.getDiameter();
        double actualCostOfLongestShortesPath = g.costOfLongestShortestPath();
        if (actualDiameter == expectedDiameter) {
        	System.out.println("Passed diameter test.");
        } else {
            System.out.println("FAILED diameter test. "
            		+ "Expected = "  + expectedDiameter +
            		" Actual = " + actualDiameter);
        }
        if (actualCostOfLongestShortesPath == expectedCostOfLongestShortestPath) {
        	System.out.println("Passed cost of longest shortest path. test.");
        } else {
        	System.out.println("FAILED cost of longest shortest path. "
        			+ "Expected = "  + expectedCostOfLongestShortestPath +
        			" Actual = " + actualCostOfLongestShortesPath);
        }
        System.out.println();
    }

    private static void doRankTests(FootballRanker ranker) {
        System.out.println("\nTESTS ON FOOTBALL TEAM GRAPH WITH FootBallRanker CLASS: \n");
        double actualError = ranker.doUnweighted(false);
        if (actualError == 10.1) {
            System.out.println("Passed unweighted test");
        } else {
            System.out.println("FAILED UNWEIGHTED ROOT MEAN SQUARE ERROR TEST. Expected 13.7, actual: " + actualError);
        }
        actualError = ranker.doWeighted(false);
        if (actualError == 8.5) {
            System.out.println("Passed weigthed test");
        } else {
            System.out.println("FAILED WEIGHTED ROOT MEAN SQUARE ERROR TEST. Expected 12.6, actual: " + actualError);
        }
        actualError = ranker.doWeightedAndWinPercentAdjusted(false);
        if (actualError == 4.2) {
            System.out.println("Passed unweighted win percent test");
        } else {
            System.out.println("FAILED WEIGHTED  AND WIN PERCENT ROOT MEAN SQUARE ERROR TEST. Expected 6.3, actual: " + actualError);
        }
    }
}

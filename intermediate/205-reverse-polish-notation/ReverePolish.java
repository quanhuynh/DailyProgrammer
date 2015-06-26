import java.util.*;

public class ReverePolish {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		while (input.hasNextLine()) {
			String curInput = input.nextLine();
			List<String> curListString = specialTokenize(curInput);
			System.out.println(formatToRPN(curListString));
		}
		input.close();
	}
	
	private static String formatToRPN (List<String> input) {
		int i = 0;
		String outputQueue = "";
		List<String> stack =  new ArrayList<String>();
		while (i < input.size()) {
			char curChar = input.get(i).charAt(0);
			String curString = "" + curChar;
			
			if (Character.isDigit(curChar)) {
				outputQueue += curString + " ";
			} else if (isOperator(curString)) {
				if (!stack.isEmpty()) {
					String topOperator = stack.get(stack.size()-1);
					while (lessOrEqual(curString, topOperator)) {
						outputQueue += topOperator + " ";
						stack.remove(stack.size()-1);
						if (stack.isEmpty()) {
							break;
						} else {
							topOperator = stack.get(stack.size()-1);
						}
					}
				}
				stack.add(curString);
			} else if (curChar == '(') {
				stack.add(curString);
			} else if (curChar == ')') {
				while (!stack.isEmpty()) {
					String topOperator = stack.get(stack.size()-1);
					if (!(topOperator.equals("("))) {
						outputQueue += topOperator + " ";
					}
					stack.remove(stack.size()-1);
				}
			}
			i++;
		}
		while (stack.size() > 0) {
			String topOperator = stack.get(stack.size()-1);
			outputQueue += topOperator + " ";
			stack.remove(stack.size()-1);
		}
		return outputQueue;
	}
	
	private static boolean isOperator (String token) {
		return "+-/*".contains(token);
	}
	
	private static boolean lessOrEqual(String op1, String op2) {
		if (op2.equals("(")) {
			return false;
		} else if (op1.equals("+") || op1.equals("-")) {
			return true;
		} else if (op1.equals("*") || op1.equals("/")) {
			if (op2 == "*" || op2 == "/") {
				return true;
			}
			else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	private static List<String> specialTokenize (String input) {
		List<String> tokenized = new ArrayList<String>();
		for (int i=0; i<input.length(); i++) {
			char curChar = input.charAt(i);
			if (curChar == ' ') {
				continue;
			} else if (Character.isDigit(curChar)) {
				String curString = "" + curChar;
				int j = i+1;
				while (j<input.length() && Character.isDigit(input.charAt(j))) {
					curString += "" + input.charAt(j);
					j++;
				}
				tokenized.add(curString);
			} else {
				tokenized.add("" + curChar);
			}
		}
		return tokenized;
	}	
}

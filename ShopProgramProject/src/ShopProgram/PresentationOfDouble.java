package ShopProgram;

import java.text.DecimalFormat;

public interface PresentationOfDouble {
	
	//Formating Double by digits after point.
	public static double customFormat(String pattern, double value ) {
		      DecimalFormat myFormatter = new DecimalFormat(pattern);
		      String output = myFormatter.format(value);
		      return Double.parseDouble(output);
		   }

}

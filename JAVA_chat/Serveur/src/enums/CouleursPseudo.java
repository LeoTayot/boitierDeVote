package enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum CouleursPseudo {
	
	  BLEU("#37bae6"),
	  ROUGE ("#e63737"),
	  VERT ("#5bc973"),
	  MARRON ("#874410"),
	  INDIGO ("#373de6"),
	  VIOLET ("#8b5bc9"),
	  KAKI ("#8da329"),
	  ROSE ("#e683dd"),
	  FUSCHIA ("#e34b97"),
	  TURQUOISE ("#4be3d1"),
	  GRIS ("#919ba1"),
	  GAZON ("#337d23"),
	  JAUNE ("#ede54a"),
	  BRIQUE ("#ed754a"),
	  ORANGE ("#e69737");
	   
	  private String name = "";
	   
	  CouleursPseudo(String name){
	    this.name = name;
	  }
	   
	  public String toString(){
	    return name;
	  }
	  
	  private static final List<CouleursPseudo> VALUES =
			    Collections.unmodifiableList(Arrays.asList(values()));
	  private static final int SIZE = VALUES.size();
	  private static final Random RANDOM = new Random();

	  public static CouleursPseudo randomCouleursPseudo()  {
	    return VALUES.get(RANDOM.nextInt(SIZE));
	  }
}

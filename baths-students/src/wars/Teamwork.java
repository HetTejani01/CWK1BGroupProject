package wars; 


/**
 * Details of your team
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Teamwork
{
    private String[] details = new String[12];
       
    public Teamwork()
    {   // in each line replace the contents of the String 
        // with the details of your team member
        // Please list the member details alphabetically by surname 
        // i.e. the surname of member1 should come alphabetically 
        // before the surname of member 2...etc
        details[0] = "CS11";
        
        details[1] = "Patel";
        details[2] = "Hinalben";
        details[3] = "21002858";

        details[4] = "Patel";
        details[5] = "Vruti";
        details[6] = "21064019";

        details[7] = "Lathiya";
        details[8] = "Smit";
        details[9] = "22090572";


        details[10] = "Tejani";
        details[11] = "Het";
        details[12] = "22086967";

	
	   // only if applicable
        details[13] = "surname of member5";
        details[14] = "first name of member5";
        details[15] = "SRN of member5";


    }
    
    public String[] getTeamDetails()
    {
        return details;
    }
    
    public void displayDetails()
    {
        for(String temp:details)
        {
            System.out.println(temp.toString());
        }
    }
}
        

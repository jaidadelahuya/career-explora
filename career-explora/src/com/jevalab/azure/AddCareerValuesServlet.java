package com.jevalab.azure;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jevalab.azure.persistence.CareerValues;
import com.jevalab.azure.persistence.CareerValuesJpaController;
import com.jevalab.exceptions.RollbackFailureException;

public class AddCareerValuesServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String vals = "Advancement * opportunity to improve my position and pay,"
				+ "Adventure * things that involve risk or uncertainty thrill me,"
				+"Benefits * healthcare and  personal benefits for me,"
				+"Challenge * situations that push me to be better,"
				+"Change and variety * varying tasks,"
				+"Community involvement * work that impacts larger community,"
				+"Competition * comparing work with co-workers for the purpose of winning,"
				+"Co-operation * working with others ,"
				+"Creativity * using my  imagination,"
				+"Economic security * receiving a rate of pay that meets my needs,"
				+"Physical Activity * staying physically active at work,"
				+"Problem solving * figuring out how to move forward,"
				+"Education * chance to further my schooling,"
				+"Entrepreneurship *  opportunities to create my own business outside work,"
				+"Environment * the atmosphere in which  I work ,"
				+"Excitement * passion about what I do,"
				+"Fast Pace * I enjoy a fast environment,"
				+"Friendly Co-workers * pleasant and caring co-workers,"
				+"Good income * earning a lot of money,"
				+"Helping others * work that benefits,"
				+"Honesty * being told the truth and telling the truth myself,"
				+"Improving society * work that would benefit society as a whole,"
				+"Routine * doing the same tasks each day,"
				+"Safety * no risk to myself or others,"
				+"Independence  * working with little direction ,"
				+"Influence over others	* affecting others’ work,"
				+"Interpersonal relations * having relations with others,"
				+"Interesting work * work that motivates me ,"
				+"Leadership * direct and  influence others,"
				+"Learning opportunities * chance to grow personally,"
				+"Leisure * time away from work,"
				+"Making decisions * deciding how things should be done,"
				+"Management * organizing and controlling situations,"
				+"Mentoring * opportunity to train others,"
				+"Teamwork * ability to work with others ,"
				+"Time for flexibility* flexible work schedule and ability to schedule my own time,"
				+"Public contact * interacting with community members,"
				+"Recognition * getting thanked for work done well,"
				+"Responsibility * deciding how things will be ,"
				+"Rewards * extra incentives for work well done,"
				+"Risk * unknown possibilities,"
				+"Schedule * having an outline of when work needs to be done,"
				+"Self-expression * adding a personal touch to my work,"
				+"Social interaction * time to interact with others,"
				+"Stability * being assured of a job and job security,"
				+"Status * having position of importance within a workplace,"
				+"Travel * work that allows me to go to different places,"
				+"Work under pressure * work under pressure ,"
				+"Work alone * doing things without much contact with others,"
				+"Work with others * doing things in teams or with others,";
		
		 CareerValues cv = new CareerValues();
		 List<String> values = Arrays.asList(vals.split(","));
		 CareerValuesJpaController cont = new CareerValuesJpaController();
		 cv.setValues(values);
		 try {
			cont.create(cv);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 resp.setContentType("text/html");
		 resp.getWriter().write("complete");
		
	}
}

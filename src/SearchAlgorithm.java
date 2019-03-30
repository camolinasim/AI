public class SearchAlgorithm {

  // Your search algorithm should return a solution in the form of a valid
  // schedule before the deadline given (deadline is given by system time in ms)
  public Schedule solve(SchedulingProblem problem, long deadline) {

    // get an empty solution to start from
    Schedule solution = problem.getEmptySchedule();

    // YOUR CODE HERE

    return solution;
  }

  // This is a very naive baseline scheduling strategy
  // It should be easily beaten by any reasonable strategy
  public Schedule naiveBaseline(SchedulingProblem problem, long deadline) {

    // get an empty solution to start from
    Schedule solution = problem.getEmptySchedule();

    for (int i = 0; i < problem.courses.size(); i++) {
      Course c = problem.courses.get(i);
      boolean scheduled = false;
      for (int j = 0; j < c.timeSlotValues.length; j++) {
        if (scheduled) break;
        if (c.timeSlotValues[j] > 0) {
          for (int k = 0; k < problem.rooms.size(); k++) {
            if (solution.schedule[k][j] < 0) {
              solution.schedule[k][j] = i;
              scheduled = true;
              break;
            }
          }
        }
      }
    }

    return solution;
  }
  
  public Schedule simulatedAnnealing(SchedulingProblem problem, long deadline) {
      double tMax = 3000;
      double tMin = 0;
      Schedule current = problem.getEmptySchedule();
      double currentScore = problem.evaluateSchedule(current);
      Schedule bestSolution = current;
      double bestScore=0;
     

      //C= Cinit
      for (int i = 0; i < problem.courses.size(); i++) {
          Course c = problem.courses.get(i);
          boolean scheduled = false;
          for (int j = 0; j < c.timeSlotValues.length; j++) {
              if (scheduled)
                  break;
              if (c.timeSlotValues[j] > 0) {
                  for (int k = 0; k < problem.rooms.size(); k++) {
                      if (current.schedule[k][j] < 0) {
                          current.schedule[k][j] = i;
                          scheduled = true;
                          break;
                      }
                  }
              }
          }
      }
      
      double timeLeft = deadline - System.currentTimeMillis();
      for(double T = tMax ; T<=tMin; T =- 0.0001 ) {
    	  if(timeLeft<=0)
    		  break;
    	  
    	  currentScore =problem.evaluateSchedule(current); //evaluate current position (Ec=E(c)
    	  /* generating successors */
          Schedule next = current.nextArrangement(problem);
          double nextScore = problem.evaluateSchedule(next); //N = next(c)
          double delta = nextScore - currentScore;
		if(delta >0) {
          bestScore = nextScore; //probably useless
          bestSolution = next;
          current = next;
		}
		else if(Math.exp(delta/T) > Math.random()){
			current=next;
		}
		  timeLeft = deadline - System.currentTimeMillis();
      }//for loop
      
      
      
	return bestSolution;
  }
  
  
}

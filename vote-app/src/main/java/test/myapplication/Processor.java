package test.myapplication;

import java.util.ArrayList;

public class Processor
{
  ArrayList<Message> listMsg  = new ArrayList<Message>();
  ArrayList<Candidate> listCand = new ArrayList<Candidate>();

  boolean run = false;
  int dup     = 0;
  int numCand = 0;

  public void addMessage(Message m) { listMsg.add(m); }

  public void createCandidates(int n)
  {
    if (n > numCand)
      for (int i = numCand + 1; i <= n; i++)
        listCand.add(new Candidate(i));
    else if (n < numCand)
      for (int i = numCand - 1; i < numCand; i++)
        listCand.remove(i);
    numCand = n;
  }

  public ArrayList<Candidate> getCandidateList() { return listCand; }
  public int getNumCandidates()  { return numCand;}
  public void runServer()        { run = true; }
  public void stopServer()       { run = false; }
  public boolean isRunning()     { return run; }
  public void dupToggle()        { dup++; }
  public boolean allowDupes()    { return !(dup % 2 == 0); }
  public void voteFor(int index) { listCand.get(index).voteCandidate(); }

  public void clearVotes()
  {
    for (Candidate c : listCand)
      c.clearVotes();
  }

  public int hasCandidate(int candID)
  {
    int index = -1;
    for (int i = 0; i < numCand; i++)
      if (listCand.get(i).getCandID() == candID)
        index = i;
    return index;
  }

  public void addCandidate(int candID)
  {
    listCand.add(new Candidate(candID));
    numCand++;
  }


  public void remCandidate(int candIndex)
  {
    listCand.remove(candIndex);
    numCand--;
  }

  public void sortByID()
  {
    for (int i = 0; i < numCand; i++)
    {
      int min = i;
      for (int j = i; j < numCand; j++)
        if (listCand.get(min).getCandID() > listCand.get(j).getCandID())
          min = j;
      Candidate c = listCand.get(min);
      listCand.set(min, listCand.get(i));
      listCand.set(i, c);
    }
  }

  public void sortByVote()
  {
    for (int i = 0; i < numCand; i++)
    {
      int min = i;
      for (int j = i; j < numCand; j++)
        if (listCand.get(min).getNumVotes() < listCand.get(j).getNumVotes())
          min = j;
      Candidate c = listCand.get(min);
      listCand.set(min, listCand.get(i));
      listCand.set(i, c);
    }
  }
}

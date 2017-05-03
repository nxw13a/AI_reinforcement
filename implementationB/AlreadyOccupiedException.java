package implementationB;

public class AlreadyOccupiedException extends RuntimeException
{
   public AlreadyOccupiedException(String msg)
   {
      super(msg);
   }
}
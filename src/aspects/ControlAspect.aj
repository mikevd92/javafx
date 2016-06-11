package aspects;

import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;

public aspect ControlAspect	extends ObserverPattern {
	
	@DeclareParents("presentation.ScreenLoader.UserPlaceListener")
	Observer ObserverInterface;
	
	@DeclareParents("service.UserService")
	Subject SubjectInterface;
	
	@DeclareParents("presentation.ScreenLoader.ManagerPlayListener")
	Observer SecondObserverInterface;

	@DeclareParents("service.ManagerService")
	Subject SecondSubjectInterface;

	@Pointcut("execution(@aspects.SubjectChanged * service.*.*(..))&&target(s)")
	protected void subjectChange(Subject s) {
	
	}
}

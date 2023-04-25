package Spring.global.error;

public class EntityAlreadyExistException extends BusinessException {

    public EntityAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}

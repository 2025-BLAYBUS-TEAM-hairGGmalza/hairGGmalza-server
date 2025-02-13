package hair.hairgg.exception;

public record ErrorResponse(int status,String code, String message) {
}

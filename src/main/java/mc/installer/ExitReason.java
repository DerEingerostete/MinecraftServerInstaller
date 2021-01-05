package mc.installer;

import java.util.HashMap;
import java.util.Map;

public enum ExitReason {
    PROGRAM_REACHED_END(0),
    UNKNOWN_EXCEPTION(1999),
    NO_OS_SUPPORT(1001),
    UNSUPPORTED_JAVA_VERSION(1002),
    NOT_ENOUGH_RAM(1003),
    NOT_ACCEPTED_EULA(1004),
    MC_VERSION_NOT_FOUND(1005),
    CUSTOM_DIRECTORY_IS_A_FILE(1006),
    MC_VERSION_ALREADY_SET(1007),
    NO_PERMISSIONS(1008);

    public static final Map<Integer, ExitReason> exitReasons = new HashMap<>();

    public static void fill() {
        if (exitReasons.isEmpty()) {
            exitReasons.put(UNKNOWN_EXCEPTION.getId(), UNKNOWN_EXCEPTION);
            exitReasons.put(NO_OS_SUPPORT.getId(), NO_OS_SUPPORT);
            exitReasons.put(UNSUPPORTED_JAVA_VERSION.getId(), UNSUPPORTED_JAVA_VERSION);
            exitReasons.put(NOT_ENOUGH_RAM.getId(), NOT_ENOUGH_RAM);
            exitReasons.put(NOT_ACCEPTED_EULA.getId(), NOT_ACCEPTED_EULA);
            exitReasons.put(PROGRAM_REACHED_END.getId(), PROGRAM_REACHED_END);
            exitReasons.put(MC_VERSION_NOT_FOUND.getId(), MC_VERSION_NOT_FOUND);
            exitReasons.put(CUSTOM_DIRECTORY_IS_A_FILE.getId(), CUSTOM_DIRECTORY_IS_A_FILE);
            exitReasons.put(MC_VERSION_ALREADY_SET.getId(), MC_VERSION_ALREADY_SET);
            exitReasons.put(NO_PERMISSIONS.getId(), NO_PERMISSIONS);
        }
    }

    public static ExitReason fromCode(int code) {
        return exitReasons.get(code);
    }

    private final int id;

    ExitReason(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}

package io.github.mroncatto.itflow.domain.user.helper;

public class RolesHelper {
    public static final String ADMIN = "ADMIN";
    public static final String MANAGER = "MANAGER";
    public static final String COORDINATOR = "COORDINATOR";
    public static final String INFRA = "INFRA";
    public static final String DEVOPS = "DEVOPS";
    public static final String HELPDESK = "HELPDESK";
    public static final String SUPPORT = "SUPPORT";
    public static final String TRAINEE = "TRAINEE";

    public static String hasAdminOnly() {
        return "hasAnyAuthority({'" + ADMIN + "'})";
    }

    public static String hasManagerOrGreater() {
        return "hasAnyAuthority({'" + MANAGER + "','" + ADMIN + "'})";
    }

    public static String hasCoordinatorOrGreater() {
        return "hasAnyAuthority({'" + COORDINATOR + "','" + MANAGER + "','" + ADMIN + "'})";
    }
}

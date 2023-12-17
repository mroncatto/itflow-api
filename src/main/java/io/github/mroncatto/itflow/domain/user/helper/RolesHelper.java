package io.github.mroncatto.itflow.domain.user.helper;

public class RolesHelper {

    //FIXME: Corrigir formato de roles
    private static final String ADMIN = "ADMIN";
    private static final String MANAGER = "MANAGER";
    private static final String COORDINATOR = "COORDINATOR";
    private static final String INFRA = "INFRA";
    private static final String DEVOPS = "DEVOPS";
    private static final String HELPDESK = "HELPDESK";
    private static final String SUPPORT = "SUPPORT";


    public static final String ADMIN_ONLY = "hasAnyAuthority({'" + ADMIN + "'})";
    public static final String MANAGER_ONLY = "hasAnyAuthority({'" + MANAGER + "'})";
    public static final String COORDINATOR_ONLY = "hasAnyAuthority({'" + COORDINATOR + "'})";
    public static final String INFRA_ONLY = "hasAnyAuthority({'" + INFRA + "'})";
    public static final String DEVOPS_ONLY = "hasAnyAuthority({'" + DEVOPS + "'})";
    public static final String HELPDESK_ONLY = "hasAnyAuthority({'" + HELPDESK + "'})";
    public static final String SUPPORT_ONLY = "hasAnyAuthority({'" + SUPPORT + "'})";
    public static final String MANAGER_OR_ADMIN = "hasAnyAuthority({'" + MANAGER + "','" + ADMIN + "'})";
    public static final String HELPDESK_OR_ADMIN = "hasAnyAuthority({'" + HELPDESK + "','" + ADMIN + "'})";
    public static final String COORDINATOR_OR_MANAGER_OR_ADMIN = "hasAnyAuthority({'" + COORDINATOR + "','" + MANAGER + "','" + ADMIN + "'})";
    public static final String HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN = "hasAnyAuthority({'" + HELPDESK + "','" + COORDINATOR + "','" + MANAGER + "','" + ADMIN + "'})";
}

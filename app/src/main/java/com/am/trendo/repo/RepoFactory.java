package com.am.trendo.repo;

public final class RepoFactory {
    public static DataRepo getDataRepo(){
        return AppDataRepo.getInstance();
    }
}

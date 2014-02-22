package com.nowellpoint.handler.dataimport.task;

import javax.persistence.EntityManagerFactory;

import com.nowellpoint.handler.dataimport.config.Entity;
import com.sforce.soap.partner.PartnerConnection;

public abstract class ImportTask {

	public abstract void run(EntityManagerFactory entityManagerFactory, PartnerConnection connection, Entity entity) throws Exception;
}
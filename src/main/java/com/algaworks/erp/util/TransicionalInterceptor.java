package com.algaworks.erp.util;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


@Interceptor
@Transacional
@Priority(Interceptor.Priority.APPLICATION)
public class TransicionalInterceptor  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@AroundInvoke
	public Object invoke(InvocationContext context) throws Exception{
		EntityTransaction trx = manager.getTransaction();
		
		boolean criador = false;
		try {
			if(!trx.isActive()) {
				//truque para fazer rollback no que ja passou
				//senao um futuro commit confirmaria ate mesmo operacoes sem transacao
				trx.begin();
				trx.rollback();
				//agr sim inicia a transicao
				trx.begin();
				criador = true;
			}
			return context.proceed();
		}catch(Exception e) {
			if(trx != null && criador) {
				trx.rollback();
			}
			throw e;
		}finally {
			if (trx != null && trx.isActive() && criador) {
				trx.commit();
			}
		}
		
	}

}


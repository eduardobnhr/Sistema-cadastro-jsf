package com.algaworks.erp.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.erp.model.Empresa;
import com.algaworks.erp.repository.Empresas;

public class CadastroEmpresaService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Empresas empresas;
	
	public void salvar(Empresa empresa) {
		empresas.guardar(empresa);
	}
	public void excluir(Empresa empresa) {
		empresas.remover(empresa);
	}

}

package com.krishagni.catissueplus.core.biospecimen.domain.factory;

import com.krishagni.catissueplus.core.common.errors.ErrorCode;


public enum VisitErrorCode implements ErrorCode {
	NOT_FOUND,
	
	INVALID_STATUS,
	
	NAME_REQUIRED,
	
	DUP_NAME,
	
	INVALID_NAME,
	
	MANUAL_NAME_NOT_ALLOWED,
	
	INVALID_CLINICAL_DIAGNOSIS,
	
	INVALID_CLINICAL_STATUS,
	
	INVALID_MISSED_REASON,

	INVALID_MISSED_USER,
	
	SITE_REQUIRED,
	
	REF_ENTITY_FOUND,

	COMPL_VISIT_REQ,

	COMPL_OR_PENDING_VISIT_REQ,

	COMPL_OR_MISSED_VISIT_REQ,

	NO_SPR_UPLOADED,

	UNABLE_TO_LOCATE_SPR,
	
	LOCKED_SPR,
	
	NON_TEXT_SPR,
	
	INVALID_COHORT,

	NO_PRINTER_CONFIGURED,

	NO_VISITS_TO_PRINT,

	PRINT_ERROR,

	INVALID_LOOKUP_FLOW;
	
	public String code() {
		return "VISIT_" + this.name();
	}
}

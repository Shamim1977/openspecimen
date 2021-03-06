package com.krishagni.catissueplus.core.biospecimen;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.krishagni.catissueplus.core.common.util.ConfigUtil;

public class ConfigParams {
	public static final String MODULE = "biospecimen";

	public static final String PARTICIPANT_UID_PATTERN   = "participant_uid_pattern";
	
	public static final String PARTICIPANT_UID_VALIDATOR = "participant_uid_validator";
	
	public static final String MRN_RESTRICTION_ENABLED   = "mrn_restriction_enabled";
		
	public static final String MPI_PATTERN               = "mpi_pattern";
	
	public static final String MPI_VALIDATOR             = "mpi_validator";
	
	public static final String MPI_FORMAT                = "mpi_format";
	
	public static final String MPI_GENERATOR             = "mpi_generator";
	
	public static final String SPECIMEN_LABEL_PRINTER    = "specimen_label_printer";
	
	public static final String SPECIMEN_LABEL_PRINT_RULES = "specimen_label_print_rules";
	
	public static final String VISIT_LABEL_PRINTER       = "visit_label_printer";
	
	public static final String VISIT_LABEL_PRINT_RULES   = "visit_label_print_rules";
	
	public static final String SPR_DEIDENTIFIER          = "spr_deidentifier";
	
	public static final String SPR_DIR                   = "spr_dir";

	public static final String CP_EXPIRY_REM_NOTIF       = "cp_expiry_rem_notif";
	
	public static final String CP_EXPIRY_REM_REPT_INTER  = "cp_expiry_rem_rept_inter";

	public static final String UNIQUE_SPMN_LABEL_PER_CP  = "unique_spmn_label_per_cp";

	public static final String UNIQUE_SPMN_BARCODE_PER_CP = "unique_spmn_barcode_per_cp";

	public static final String CP_SOP_DOCS_DIR           = "cp_sop_doc_dir";

	public static final String CP_SOP_DOC                = "cp_sop_doc";

	public static final String SYS_WORKFLOWS             = "sys_workflows";

	public static final String SYS_RPT_SETTINGS          = "sys_cp_rpt_settings";

	public static final String SYS_SPMN_CP_RPT_SETTINGS  = "sys_spmn_cp_rpt_settings";

	public static final String EXTRACT_SPR_TEXT          = "extract_spr_text";

	public static final String PARTICIPANT_LOOKUP_FLOW   = "participant_lookup_flow";

	public static final String CONSENTS_DIR              = "participant_consent_dir";

	public static final String REL_SORTING_MAX_SPMNS     = "rel_sorting_max_spmns";

	public static final String STAGED_PART_CLEANUP_INT   = "staged_part_cleanup_int";

	public static final String ENABLE_SPMN_BARCODING     = "enable_spmn_barcoding";

	public static final String SPMN_BARCODE_FORMAT       = "specimen_barcode_format";

	public static final String ALIQUOT_LABEL_FORMAT      = "aliquot_label_format";

	public static final String VISITS_LOOKUP_FLOW        = "visits_lookup_flow";

	public static String getConsentsDirPath() {
		String path = ConfigUtil.getInstance().getStrSetting(MODULE, CONSENTS_DIR, null);
		if (StringUtils.isBlank(path)) {
			path = ConfigUtil.getInstance().getDataDir() + File.separator + "participant-consents";
		}

		File consentDir = new File(path);
		if (!consentDir.exists()) {
			consentDir.mkdirs();
		}

		return path + File.separator;
	}
}

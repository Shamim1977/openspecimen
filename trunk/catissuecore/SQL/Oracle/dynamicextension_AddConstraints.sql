alter table DYEXTN_COLUMN_PROPERTIES add constraint FK8FCE2B3FBC7298A9 foreign key (IDENTIFIER) references DYEXTN_DATABASE_PROPERTIES;
alter table DYEXTN_COLUMN_PROPERTIES add constraint FK8FCE2B3FB4C15A36 foreign key (PRIMITIVE_ATTRIBUTE_ID) references DYEXTN_PRIMITIVE_ATTRIBUTE;
alter table DYEXTN_USERDEFINED_DE add constraint FK630761FFBC7298A9 foreign key (IDENTIFIER) references DYEXTN_DATA_ELEMENT;
alter table DYEXTN_CONSTRAINT_PROPERTIES add constraint FK82886CD8BC7298A9 foreign key (IDENTIFIER) references DYEXTN_DATABASE_PROPERTIES;
alter table DYEXTN_CONSTRAINT_PROPERTIES add constraint FK82886CD8927B15B9 foreign key (ASSOCIATION_ID) references DYEXTN_ASSOCIATION;
alter table DYEXTN_PERMISSIBLE_VALUE add constraint FK136264E08C8D972A foreign key (ATTRIBUTE_TYPE_INFO_ID) references DYEXTN_ATTRIBUTE_TYPE_INFO;
alter table DYEXTN_PERMISSIBLE_VALUE add constraint FK136264E03D51114B foreign key (USER_DEF_DE_ID) references DYEXTN_USERDEFINED_DE;
alter table DYEXTN_CHECK_BOX add constraint FK4EFF9257BC7298A9 foreign key (IDENTIFIER) references DYEXTN_CONTROL;
alter table DYEXTN_TABLE_PROPERTIES add constraint FKE608E08179F466F7 foreign key (ENTITY_ID) references DYEXTN_ENTITY;
alter table DYEXTN_TABLE_PROPERTIES add constraint FKE608E081BC7298A9 foreign key (IDENTIFIER) references DYEXTN_DATABASE_PROPERTIES;
alter table DYEXTN_INTEGER_CONCEPT_VALUE add constraint FKFBA33B3CBC7298A9 foreign key (IDENTIFIER) references DYEXTN_PERMISSIBLE_VALUE;
alter table DYEXTN_STRING_CONCEPT_VALUE add constraint FKADE7D889BC7298A9 foreign key (IDENTIFIER) references DYEXTN_PERMISSIBLE_VALUE;
alter table DYEXTN_BOOLEAN_TYPE_INFO add constraint FK28F1809FBC7298A9 foreign key (IDENTIFIER) references DYEXTN_ATTRIBUTE_TYPE_INFO;
alter table DYEXTN_BOOLEAN_CONCEPT_VALUE add constraint FK57B6C4A6BC7298A9 foreign key (IDENTIFIER) references DYEXTN_PERMISSIBLE_VALUE;
alter table DE_FILE_ATTR_RECORD_VALUES add constraint FKE68334E7E150DFC9 foreign key (RECORD_ID) references DYEXTN_ATTRIBUTE_RECORD;
alter table DYEXTN_STRING_TYPE_INFO add constraint FKDA35FE02BC7298A9 foreign key (IDENTIFIER) references DYEXTN_ATTRIBUTE_TYPE_INFO;
alter table DYEXTN_SHORT_CONCEPT_VALUE add constraint FKC1945ABABC7298A9 foreign key (IDENTIFIER) references DYEXTN_PERMISSIBLE_VALUE;
alter table DYEXTN_FLOAT_TYPE_INFO add constraint FK7E1C0693BC7298A9 foreign key (IDENTIFIER) references DYEXTN_NUMERIC_TYPE_INFO;
alter table DYEXTN_FILE_UPLOAD add constraint FK2FAD41E7BC7298A9 foreign key (IDENTIFIER) references DYEXTN_CONTROL;
alter table DYEXTN_BARR_CONCEPT_VALUE add constraint FK89D27DF7BC7298A9 foreign key (IDENTIFIER) references DYEXTN_PERMISSIBLE_VALUE;
alter table DYEXTN_ENTITY add constraint FK8B243640450711A2 foreign key (PARENT_ENTITY_ID) references DYEXTN_ENTITY;
alter table DYEXTN_ENTITY add constraint FK8B243640BC7298A9 foreign key (IDENTIFIER) references DYEXTN_ABSTRACT_METADATA;
alter table DYEXTN_INTEGER_TYPE_INFO add constraint FK5F9CB235BC7298A9 foreign key (IDENTIFIER) references DYEXTN_NUMERIC_TYPE_INFO;
alter table DYEXTN_TAGGED_VALUE add constraint FKF79D055B7D7A9B8E foreign key (ABSTRACT_METADATA_ID) references DYEXTN_ABSTRACT_METADATA;
alter table DYEXTN_COMBOBOX add constraint FKABBC649ABC7298A9 foreign key (IDENTIFIER) references DYEXTN_SELECT_CONTROL;
alter table DYEXTN_PRIMITIVE_ATTRIBUTE add constraint FKA9F765C7BC7298A9 foreign key (IDENTIFIER) references DYEXTN_ATTRIBUTE;
alter table DYEXTN_ASSOCIATION add constraint FK104684243AC5160 foreign key (SOURCE_ROLE_ID) references DYEXTN_ROLE;
alter table DYEXTN_ASSOCIATION add constraint FK10468424F60C84D6 foreign key (TARGET_ROLE_ID) references DYEXTN_ROLE;
alter table DYEXTN_ASSOCIATION add constraint FK104684246315C5C9 foreign key (TARGET_ENTITY_ID) references DYEXTN_ENTITY;
alter table DYEXTN_ASSOCIATION add constraint FK10468424BC7298A9 foreign key (IDENTIFIER) references DYEXTN_ATTRIBUTE;
alter table DYEXTN_CADSRDE add constraint FK588A2509BC7298A9 foreign key (IDENTIFIER) references DYEXTN_DATA_ELEMENT;
alter table DYEXTN_DOUBLE_TYPE_INFO add constraint FKC83869C2BC7298A9 foreign key (IDENTIFIER) references DYEXTN_NUMERIC_TYPE_INFO;
alter table DYEXTN_SELECT_CONTROL add constraint FKDFEBB657BC7298A9 foreign key (IDENTIFIER) references DYEXTN_CONTROL;
alter table DYEXTN_ENTITY_GROUP_REL add constraint FK5A0D835A992A67D7 foreign key (ENTITY_GROUP_ID) references DYEXTN_ENTITY_GROUP;
alter table DYEXTN_ENTITY_GROUP_REL add constraint FK5A0D835A79F466F7 foreign key (ENTITY_ID) references DYEXTN_ENTITY;
alter table DYEXTN_FILE_TYPE_INFO add constraint FKA00F0EDBC7298A9 foreign key (IDENTIFIER) references DYEXTN_ATTRIBUTE_TYPE_INFO;
alter table DYEXTN_FILE_EXTENSIONS add constraint FKD49834FA4D87D1BE foreign key (ATTRIBUTE_ID) references DYEXTN_FILE_TYPE_INFO;
alter table DYEXTN_LONG_TYPE_INFO add constraint FK257281EDBC7298A9 foreign key (IDENTIFIER) references DYEXTN_NUMERIC_TYPE_INFO;
alter table DYEXTN_FLOAT_CONCEPT_VALUE add constraint FK6785309ABC7298A9 foreign key (IDENTIFIER) references DYEXTN_PERMISSIBLE_VALUE;
alter table DYEXTN_TEXTFIELD add constraint FKF9AFC850BC7298A9 foreign key (IDENTIFIER) references DYEXTN_CONTROL;
alter table DYEXTN_RULE add constraint FKC27E0994D87D1BE foreign key (ATTRIBUTE_ID) references DYEXTN_ATTRIBUTE;
alter table DYEXTN_DATE_TYPE_INFO add constraint FKFBA549FBC7298A9 foreign key (IDENTIFIER) references DYEXTN_ATTRIBUTE_TYPE_INFO;
alter table DYEXTN_ATTRIBUTE_TYPE_INFO add constraint FK62596D53B4C15A36 foreign key (PRIMITIVE_ATTRIBUTE_ID) references DYEXTN_PRIMITIVE_ATTRIBUTE;
alter table DYEXTN_ASSO_DISPLAY_ATTR add constraint FKD12FD3823B3AAE3B foreign key (DISPLAY_ATTRIBUTE_ID) references DYEXTN_PRIMITIVE_ATTRIBUTE;
alter table DYEXTN_ASSO_DISPLAY_ATTR add constraint FKD12FD382F7AA8E80 foreign key (SELECT_CONTROL_ID) references DYEXTN_SELECT_CONTROL;
alter table DYEXTN_TEXTAREA add constraint FK946EE257BC7298A9 foreign key (IDENTIFIER) references DYEXTN_CONTROL;
alter table DYEXTN_ENTITY_GROUP add constraint FK105DE7A0BC7298A9 foreign key (IDENTIFIER) references DYEXTN_ABSTRACT_METADATA;
alter table DYEXTN_DATA_ELEMENT add constraint FKB1153E48C8D972A foreign key (ATTRIBUTE_TYPE_INFO_ID) references DYEXTN_ATTRIBUTE_TYPE_INFO;
alter table DYEXTN_DOUBLE_CONCEPT_VALUE add constraint FKB94E6449BC7298A9 foreign key (IDENTIFIER) references DYEXTN_PERMISSIBLE_VALUE;
alter table DYEXTN_LIST_BOX add constraint FK208395A7BC7298A9 foreign key (IDENTIFIER) references DYEXTN_SELECT_CONTROL;
alter table DYEXTN_CONTAINMENT_CONTROL add constraint FK3F9D4AD3F7798636 foreign key (DISPLAY_CONTAINER_ID) references DYEXTN_CONTAINER;
alter table DYEXTN_CONTAINMENT_CONTROL add constraint FK3F9D4AD3BC7298A9 foreign key (IDENTIFIER) references DYEXTN_CONTROL;
alter table DYEXTN_LONG_CONCEPT_VALUE add constraint FK3E1A6EF4BC7298A9 foreign key (IDENTIFIER) references DYEXTN_PERMISSIBLE_VALUE;
alter table DYEXTN_CONTROL add constraint FK70FB5E80A67822BB foreign key (ABSTRACT_ATTRIBUTE_ID) references DYEXTN_ATTRIBUTE;
alter table DYEXTN_CONTROL add constraint FK70FB5E809C6A9B9 foreign key (CONTAINER_ID) references DYEXTN_CONTAINER;
alter table DYEXTN_ATTRIBUTE add constraint FK37F1E2FFB15CD09F foreign key (ENTIY_ID) references DYEXTN_ENTITY;
alter table DYEXTN_ATTRIBUTE add constraint FK37F1E2FFBC7298A9 foreign key (IDENTIFIER) references DYEXTN_ABSTRACT_METADATA;
alter table DYEXTN_RULE_PARAMETER add constraint FK22567363871AAD3E foreign key (RULE_ID) references DYEXTN_RULE;
alter table DYEXTN_DATE_CONCEPT_VALUE add constraint FK45F598A6BC7298A9 foreign key (IDENTIFIER) references DYEXTN_PERMISSIBLE_VALUE;
alter table DYEXTN_BYTE_ARRAY_TYPE_INFO add constraint FK18BDA73BC7298A9 foreign key (IDENTIFIER) references DYEXTN_ATTRIBUTE_TYPE_INFO;
alter table DYEXTN_SEMANTIC_PROPERTY add constraint FKD2A0B5B13BAB5E46 foreign key (ABSTRACT_VALUE_ID) references DYEXTN_PERMISSIBLE_VALUE;
alter table DYEXTN_SEMANTIC_PROPERTY add constraint FKD2A0B5B17D7A9B8E foreign key (ABSTRACT_METADATA_ID) references DYEXTN_ABSTRACT_METADATA;
alter table DYEXTN_ATTRIBUTE_RECORD add constraint FK9B20ED9179F466F7 foreign key (ENTITY_ID) references DYEXTN_ENTITY;
alter table DYEXTN_ATTRIBUTE_RECORD add constraint FK9B20ED914D87D1BE foreign key (ATTRIBUTE_ID) references DYEXTN_PRIMITIVE_ATTRIBUTE;
alter table DYEXTN_DATA_GRID add constraint FK233EB73EBC7298A9 foreign key (IDENTIFIER) references DYEXTN_CONTROL;
alter table DE_COLL_ATTR_RECORD_VALUES add constraint FK847DA577355836BC foreign key (COLLECTION_ATTR_RECORD_ID) references DYEXTN_ATTRIBUTE_RECORD;
alter table DYEXTN_NUMERIC_TYPE_INFO add constraint FK4DEC9544BC7298A9 foreign key (IDENTIFIER) references DYEXTN_ATTRIBUTE_TYPE_INFO;
alter table DYEXTN_SHORT_TYPE_INFO add constraint FK99540B3BC7298A9 foreign key (IDENTIFIER) references DYEXTN_NUMERIC_TYPE_INFO;
alter table DYEXTN_CONTAINER add constraint FK1EAB84E4A1257067 foreign key (BASE_CONTAINER_ID) references DYEXTN_CONTAINER;
alter table DYEXTN_CONTAINER add constraint FK1EAB84E479F466F7 foreign key (ENTITY_ID) references DYEXTN_ENTITY;
alter table DYEXTN_CONTAINER add constraint FK1EAB84E4992A67D7 foreign key (ENTITY_GROUP_ID) references DYEXTN_ENTITY_GROUP;
alter table DYEXTN_CONTAINER add constraint FK1EAB84E445DEFCF5 foreign key (VIEW_ID) references DYEXTN_VIEW;
alter table DYEXTN_RADIOBUTTON add constraint FK16F5BA90BC7298A9 foreign key (IDENTIFIER) references DYEXTN_CONTROL;
alter table DYEXTN_DATEPICKER add constraint FKFEADD199BC7298A9 foreign key (IDENTIFIER) references DYEXTN_CONTROL;
create sequence DYEXTN_SEMANTIC_PROPERTY_SEQ;
create sequence DYEXTN_PERMISSIBLEVAL_SEQ;
create sequence DYEXTN_ATTRIBUTE_TYPE_INFO_SEQ;
create sequence DYEXTN_RULE_PARAMETER_SEQ;
create sequence DE_FILE_ATTR_REC_VALUES_SEQ;
create sequence DYEXTN_ABSTRACT_METADATA_SEQ;
create sequence DYEXTN_VIEW_SEQ;
create sequence DYEXTN_CONTROL_SEQ;
create sequence DYEXTN_RULE_SEQ;
create sequence DYEXTN_DATA_ELEMENT_SEQ;
create sequence DE_ATTR_REC_SEQ;
create sequence DYEXTN_CONTAINER_SEQ;
create sequence DYEXTN_FILE_EXTN_SEQ;
create sequence DYEXTN_ASSO_DISPLAY_ATTR_SEQ;
create sequence DYEXTN_DATABASE_PROPERTIES_SEQ;
create sequence DYEXTN_ROLE_SEQ;
create sequence DYEXTN_TAGGED_VALUE_SEQ;
create sequence DE_COLL_ATTR_REC_VALUES_SEQ;
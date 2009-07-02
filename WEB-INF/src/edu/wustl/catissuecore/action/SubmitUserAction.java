
package edu.wustl.catissuecore.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import edu.wustl.catissuecore.domain.User;
import edu.wustl.catissuecore.dto.UserDTO;
import edu.wustl.catissuecore.multiRepository.bean.SiteUserRolePrivilegeBean;
import edu.wustl.catissuecore.util.global.Constants;
import edu.wustl.common.actionForm.AbstractActionForm;
import edu.wustl.common.beans.AddNewSessionDataBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.domain.AbstractDomainObject;
import edu.wustl.common.exception.ApplicationException;
import edu.wustl.common.exception.AssignDataException;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.exception.ErrorKey;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.factory.IDomainObjectFactory;
import edu.wustl.common.factory.IFactory;
import edu.wustl.common.factory.IForwordToFactory;
import edu.wustl.common.util.AbstractForwardToProcessor;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.global.Status;
import edu.wustl.common.util.global.Validator;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.DAO;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.dao.util.HibernateMetaData;
import edu.wustl.simplequery.bizlogic.QueryBizLogic;

/**
 * @author renuka_bajpai
 */
public class SubmitUserAction extends Action
{

	/**
	 * logger.
	 */
	private transient Logger logger = Logger.getCommonLogger(SubmitUserAction.class);

	/**
	 * target.
	 */
	String target = null;

	/**
	 * abstractDomain.
	 */
	AbstractDomainObject abstractDomain = null;

	/**
	 * messages.
	 */
	ActionMessages messages = null;

	/**
	 * Overrides the executeSecureAction method of SecureAction class.
	 *
	 * @param mapping
	 *            object of ActionMapping
	 * @param form
	 *            object of ActionForm
	 * @param request
	 *            object of HttpServletRequest
	 * @param response
	 *            object of HttpServletResponse
	 * @throws AssignDataException
	 *             generic exception
	 * @return ActionForward : ActionForward
	 */

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws AssignDataException
	{
		try
		{
			AbstractActionForm abstractForm = (AbstractActionForm) form;
			if (abstractForm.isAddOperation())
			{

				return executeAddUser(mapping, request, abstractForm);
			}
			else
			{
				return executeEditUser(mapping, request, abstractForm);
			}
		}
		catch (BizLogicException excp)
		{
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("errors.item", excp.getMessage());
			errors.add(ActionErrors.GLOBAL_ERROR, error);
			saveErrors(request, errors);
			target = new String(Constants.FAILURE);

			logger.error(excp.getMessage(), excp);
		}
		catch (DAOException excp)
		{
			target = Constants.FAILURE;
			logger.error(excp.getMessage(), excp);
		}
		catch (ApplicationException excep)
		{
			target = Constants.FAILURE;
			logger.error(excep.getMessage(), excep);
		}
		return mapping.findForward(target);
	}

	/**
	 * @param mapping
	 *            : mapping
	 * @param request
	 *            : request
	 * @param form
	 *            : form
	 * @return ActionForward : ActionForward
	 * @throws ApplicationException
	 *             : ApplicationException
	 */
	private ActionForward executeAddUser(ActionMapping mapping, HttpServletRequest request,
			AbstractActionForm form) throws ApplicationException
	{
		User user = null;
		IDomainObjectFactory abstractDomainObjectFactory = getIDomainObjectFactory();
		AbstractActionForm form1 = (AbstractActionForm) form;
		user = (User) abstractDomainObjectFactory.getDomainObject(form1.getFormId(), form1);
		AbstractActionForm abstractForm = (AbstractActionForm) form;
		String objectName = abstractDomainObjectFactory.getDomainObjectName(abstractForm
				.getFormId());
		// UserBizLogic bizLogic = (UserBizLogic)
		// BizLogicFactory.getInstance().getBizLogic(Constants.USER_FORM_ID);
		HttpSession session = request.getSession();
		// SessionDataBean sessionDataBean = (SessionDataBean)
		// session.getAttribute(Constants.SESSION_DATA);
		// bizLogic.insert(user, sessionDataBean, 0);
		target = new String(Constants.SUCCESS);
		AbstractDomainObject abstractDomain = (AbstractDomainObject) user;
		UserDTO userDTO = getUserDTO(user, session);

		insertUser(userDTO, request.getSession(), form);

		// Attributes to decide AddNew action
		String submittedFor = (String) request.getParameter(Constants.SUBMITTED_FOR);
		request.setAttribute(Constants.SYSTEM_IDENTIFIER, user.getId());
		logger.debug("Checking parameter SubmittedFor in CommonAddEditAction--->"
				+ request.getParameter(Constants.SUBMITTED_FOR));
		logger.debug("SubmittedFor attribute of Form-Bean received---->"
				+ abstractForm.getSubmittedFor());
		IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
		IBizLogic queryBizLogic = factory
				.getBizLogic(edu.wustl.common.util.global.Constants.QUERY_INTERFACE_ID);
		messages = new ActionMessages();

		addMessage(messages, abstractDomain, "add", (QueryBizLogic) queryBizLogic, objectName);

		if ((submittedFor != null) && (submittedFor.equals("AddNew")))
		{
			logger.debug("SubmittedFor is AddNew in CommonAddEditAction...................");

			Stack formBeanStack = (Stack) session.getAttribute(Constants.FORM_BEAN_STACK);

			if (formBeanStack != null)
			{

				AddNewSessionDataBean addNewSessionDataBean = (AddNewSessionDataBean) formBeanStack
						.pop();

				if (addNewSessionDataBean != null)
				{
					AbstractActionForm sessionFormBean = addNewSessionDataBean
							.getAbstractActionForm();

					String forwardTo = addNewSessionDataBean.getForwardTo();
					logger.debug("forwardTo in CommonAddEditAction--------->" + forwardTo);
					sessionFormBean.setAddNewObjectIdentifier
					(addNewSessionDataBean.getAddNewFor(),
							abstractDomain.getId());
					sessionFormBean.setMutable(false);
					if (formBeanStack.isEmpty())
					{
						session.removeAttribute(Constants.FORM_BEAN_STACK);
						request.setAttribute(Constants.SUBMITTED_FOR, "Default");
						logger
								.debug("SubmittedFor set as Default in" +
										" CommonAddEditAction===========");

						logger.debug("cleaning FormBeanStack from session*************");
					}
					else
					{
						request.setAttribute(Constants.SUBMITTED_FOR, "AddNew");
					}
					String formBeanName = Utility.getFormBeanName(sessionFormBean);
					request.setAttribute(formBeanName, sessionFormBean);

					logger.debug("InitiliazeAction operation=========>"
							+ sessionFormBean.getOperation());

					if (messages != null)
					{
						saveMessages(request, messages);
					}
					String statusMessageKey = String.valueOf(abstractForm.getFormId() + "."
							+ String.valueOf(abstractForm.isAddOperation()));
					request.setAttribute(Constants.STATUS_MESSAGE_KEY, statusMessageKey);
					if ((sessionFormBean.getOperation().equals("edit")))
					{
						logger
								.debug("Edit object Identifier while AddNew " +
										"is from Edit operation==>"
										+ sessionFormBean.getId());
						ActionForward editForward = new ActionForward();

						String addPath = (mapping.findForward(forwardTo)).getPath();
						logger.debug("Operation before edit==========>" + addPath);

						String editPath = addPath.replaceFirst
						("operation=add", "operation=edit");
						logger.debug("Operation edited=============>" + editPath);
						editForward.setPath(editPath);

						return editForward;
					}
					return (mapping.findForward(forwardTo));
				}
				else
				{
					target = new String(Constants.FAILURE);

					ActionErrors errors = new ActionErrors();
					ActionError error = new ActionError
					("errors.item.unknown", AbstractDomainObject
							.parseClassName(objectName));
					errors.add(ActionErrors.GLOBAL_ERROR, error);
					saveErrors(request, errors);
				}
			}
		}
		else if ((submittedFor != null) && (submittedFor.equals("ForwardTo")))
		{
			logger.debug("SubmittedFor is ForwardTo in CommonAddEditAction...................");
			request.setAttribute(Constants.SUBMITTED_FOR, "Default");
			request.setAttribute("forwardToHashMap", generateForwardToHashMap(abstractForm,
					abstractDomain));
		}
		if (abstractForm.getForwardTo() != null && abstractForm.getForwardTo().trim().length() > 0)
		{
			String forwardTo = abstractForm.getForwardTo();
			logger.debug("ForwardTo in Add :-- : " + forwardTo);
			target = forwardTo;
		}
		request.setAttribute("forwardToPrintMap", generateForwardToPrintMap(abstractForm,
				abstractDomain));
		if (messages != null)
		{
			saveMessages(request, messages);
		}
		String statusMessageKey = String.valueOf(abstractForm.getFormId() + "."
				+ String.valueOf(abstractForm.isAddOperation()));
		request.setAttribute(Constants.STATUS_MESSAGE_KEY, statusMessageKey);

		return mapping.findForward(target);
	}

	/**
	 * @param mapping
	 *            : mapping
	 * @param request
	 *            : request
	 * @param abstractForm
	 *            : abstractForm
	 * @return ActionForward : ActionForward
	 * @throws ApplicationException
	 *             : ApplicationException
	 */
	private ActionForward executeEditUser(ActionMapping mapping, HttpServletRequest request,
			AbstractActionForm abstractForm) throws ApplicationException
	{
		target = new String(Constants.SUCCESS);
		DAO dao = null;
		// If operation is edit, update the data in the database.

		// List list = bizLogic.retrieve(objectName,
		// Constants.SYSTEM_IDENTIFIER,
		// new Long(abstractForm.getId()));
		// if (!list.isEmpty())
		// {
		// abstractDomain = (AbstractDomainObject) list.get(0);
		// abstractDomain.setAllValues(abstractForm);
		DefaultBizLogic defaultBizLogic = new DefaultBizLogic();
		IDomainObjectFactory abstractDomainObjectFactory = getIDomainObjectFactory();
		String objectName = getObjectName(abstractDomainObjectFactory, abstractForm);
		abstractDomain = defaultBizLogic.populateDomainObject(objectName, new Long(abstractForm
				.getId()), abstractForm);
		if (abstractDomain != null)
		{
			dao = DAOConfigFactory.getInstance().getDAOFactory(Constants.APPLICATION_NAME).getDAO();

			dao.openSession(null);
			AbstractDomainObject abstractDomainOld = null;
			try
			{
				abstractDomainOld = (AbstractDomainObject) dao.retrieveById(objectName, new Long(
						abstractForm.getId()));
			}
			catch (Exception ex)
			{
				logger.debug(ex.getMessage(), ex);
				ex.printStackTrace();
			}
			HttpSession session = request.getSession();
			UserDTO userCurrent = getUserDTO((User) abstractDomain, session);
			User userOld = (User) abstractDomainOld;

			updateUser(userCurrent, userOld, request.getSession(), abstractForm);

			try
			{
				dao.closeSession();
			}
			catch (Exception ex)
			{
				logger.debug(ex.getMessage(), ex);
				ex.printStackTrace();
			}

			if ((abstractForm.getActivityStatus() != null)
					&& (Status.ACTIVITY_STATUS_DISABLED.toString().equals(abstractForm
							.getActivityStatus())))
			{
				String moveTo = abstractForm.getOnSubmit();
				logger.debug("MoveTo in Disabled :-- : " + moveTo);
				ActionForward reDirectForward = new ActionForward();
				reDirectForward.setPath(moveTo);
				return reDirectForward;
			}

			if (abstractForm.getOnSubmit() != null
					&& abstractForm.getOnSubmit().trim().length() > 0)
			{
				String forwardTo = abstractForm.getOnSubmit();
				logger.debug("OnSubmit :-- : " + forwardTo);
				return (mapping.findForward(forwardTo));
			}

			String submittedFor = (String) request.getParameter(Constants.SUBMITTED_FOR);
			logger.debug("Submitted for in Edit CommonAddEditAction===>" + submittedFor);

			if ((submittedFor != null) && (submittedFor.equals("ForwardTo")))
			{
				logger.debug("SubmittedFor is ForwardTo in" +
						" CommonAddEditAction...");

				request.setAttribute(Constants.SUBMITTED_FOR, "Default");

				request.setAttribute("forwardToHashMap", generateForwardToHashMap(abstractForm,
						abstractDomain));
			}

			if (abstractForm.getForwardTo() != null
					&& abstractForm.getForwardTo().trim().length() > 0)
			{
				String forwardTo = abstractForm.getForwardTo();
				logger.debug("ForwardTo in Edit :-- : " + forwardTo);

				target = forwardTo;
			}

			String pageOf = (String) request.getParameter(Constants.PAGE_OF);
			logger.debug("pageof for query edit==" + pageOf);
			if (pageOf != null)
			{
				if (pageOf.equals(Constants.QUERY))
				{
					target = pageOf;
				}
			}
			messages = new ActionMessages();
			IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
			IBizLogic queryBizLogic = factory
					.getBizLogic(edu.wustl.common.util.global.Constants.QUERY_INTERFACE_ID);
			addMessage(messages, abstractDomain, "edit", (QueryBizLogic) queryBizLogic, objectName);
		}
		else
		{
			target = new String(Constants.FAILURE);

			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("errors.item.unknown", AbstractDomainObject
					.parseClassName(objectName));
			errors.add(ActionErrors.GLOBAL_ERROR, error);
			saveErrors(request, errors);
		}
		request.setAttribute("forwardToPrintMap", generateForwardToPrintMap(abstractForm,
				abstractDomain));

		if (messages != null)
		{
			saveMessages(request, messages);
		}

		String statusMessageKey = String.valueOf(abstractForm.getFormId() + "."
				+ String.valueOf(abstractForm.isAddOperation()));
		request.setAttribute(Constants.STATUS_MESSAGE_KEY, statusMessageKey);
		return mapping.findForward(target);
	}

	/**
	 * * This method generates HashMap of data required to be forwarded if Form
	 * is submitted for Print request
	 *
	 * @param abstractForm
	 *            : abstractForm
	 * @param abstractDomain
	 *            : abstractDomain
	 * @return HashMap : HashMap
	 * @throws BizLogicException
	 *             : BizLogicException
	 */
	private HashMap generateForwardToPrintMap(AbstractActionForm abstractForm,
			AbstractDomainObject abstractDomain) throws BizLogicException
	{
		HashMap forwardToPrintMap = null;
		IForwordToFactory factory = AbstractFactoryConfig.getInstance().getForwToFactory();
		AbstractForwardToProcessor forwardToProcessor = factory.getForwardToPrintProcessor();
		forwardToPrintMap = (HashMap) forwardToProcessor.populateForwardToData(abstractForm,
				abstractDomain);
		return forwardToPrintMap;
	}

	/**
	 * @param abstractForm
	 *            : abstractForm
	 * @param abstractDomain
	 *            : abstractDomain
	 * @return HashMap : HashMap
	 * @throws BizLogicException
	 *             : BizLogicException
	 */
	private HashMap generateForwardToHashMap(AbstractActionForm abstractForm,
			AbstractDomainObject abstractDomain) throws BizLogicException
	{
		HashMap forwardToHashMap;
		IForwordToFactory factory = AbstractFactoryConfig.getInstance().getForwToFactory();
		AbstractForwardToProcessor forwardToProcessor = factory.getForwardToProcessor();
		forwardToHashMap = (HashMap) forwardToProcessor.populateForwardToData(abstractForm,
				abstractDomain);
		return forwardToHashMap;
	}

	/**
	 * @param abstractDomainObjectFactory
	 *            : abstractDomainObjectFactory
	 * @param abstractForm
	 *            : abstractForm
	 * @return String : String
	 */
	public String getObjectName(IDomainObjectFactory abstractDomainObjectFactory,
			AbstractActionForm abstractForm)
	{
		return abstractDomainObjectFactory.getDomainObjectName(abstractForm.getFormId());
	}

	/**
	 * @param user
	 *            : user
	 * @param session
	 *            : session
	 * @return UserDTO : UserDTO
	 */
	private UserDTO getUserDTO(User user, HttpSession session)
	{
		UserDTO userDTO = new UserDTO();
		Map < String , SiteUserRolePrivilegeBean > userRowIdBeanMap =
			(Map < String , SiteUserRolePrivilegeBean >) session
				.getAttribute("rowIdBeanMapForUserPage");
		userDTO.setUser(user);
		userDTO.setUserRowIdBeanMap(userRowIdBeanMap);
		return userDTO;
	}

	/**
	 * @param userDTO
	 *            : userDTO
	 * @param session
	 *            : session
	 * @param form
	 *            : form
	 * @throws BizLogicException
	 *             : BizLogicException
	 */
	private void insertUser(UserDTO userDTO, HttpSession session, AbstractActionForm form)
			throws BizLogicException
	{
		IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
		IBizLogic bizLogic = factory.getBizLogic(form.getFormId());
		SessionDataBean sessionDataBean = (SessionDataBean) session
				.getAttribute(Constants.SESSION_DATA);
		bizLogic.insert(userDTO, sessionDataBean, 0);
	}

	private void updateUser(UserDTO userCurrent, User userOld, HttpSession session,
			AbstractActionForm form) throws BizLogicException
	{
		IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
		IBizLogic bizLogic = factory.getBizLogic(form.getFormId());
		SessionDataBean sessionDataBean = (SessionDataBean) session
				.getAttribute(Constants.SESSION_DATA);
		bizLogic.update(userCurrent, userOld, 0, sessionDataBean);
	}

	/**
	 * This method will add the success message into ActionMessages object
	 *
	 * @param messages
	 *            ActionMessages
	 * @param abstractDomain
	 *            AbstractDomainObject
	 * @param addoredit
	 *            String
	 * @param queryBizLogic
	 *            QueryBizLogic
	 * @param objectName
	 *            String
	 */
	private void addMessage(ActionMessages messages, AbstractDomainObject abstractDomain,
			String addoredit, QueryBizLogic queryBizLogic, String objectName)
	{
		String message = abstractDomain.getMessageLabel();
		Validator validator = new Validator();
		boolean isEmpty = validator.isEmpty(message);
		String displayName = null;
		try
		{
			displayName = queryBizLogic.getDisplayNamebyTableName(HibernateMetaData
					.getTableName(abstractDomain.getClass()));
		}
		catch (Exception excp)
		{
			displayName = AbstractDomainObject.parseClassName(objectName);
			logger.error(excp.getMessage(), excp);
		}

		if (!isEmpty)
		{
			messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("object." + addoredit
					+ ".success", displayName, message));
		}
		else
		{
			messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("object." + addoredit
					+ ".successOnly", displayName));
		}
	}

	/**
	 * @return IDomainObjectFactory : IDomainObjectFactory
	 * @throws ApplicationException
	 *             : ApplicationException
	 */
	public IDomainObjectFactory getIDomainObjectFactory() throws ApplicationException
	{
		try
		{
			IDomainObjectFactory factory = AbstractFactoryConfig.getInstance()
					.getDomainObjectFactory();
			return factory;
		}
		catch (BizLogicException exception)
		{
			// logger.error(
			// "Failed to get QueryBizLogic object from BizLogic Factory");
			throw new ApplicationException(ErrorKey.getErrorKey("errors.item"), exception,
					"Failed to get DomainObjectFactory in base Add/Edit.");
		}
	}
}

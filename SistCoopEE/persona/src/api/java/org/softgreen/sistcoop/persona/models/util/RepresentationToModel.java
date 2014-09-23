package org.softgreen.sistcoop.persona.models.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.codec.binary.Base64;
import org.jboss.logging.Logger;
import org.softgreen.sistcoop.persona.enums.EstadoCivil;
import org.softgreen.sistcoop.persona.enums.Sexo;
import org.softgreen.sistcoop.persona.enums.TipoPersona;
import org.softgreen.sistcoop.persona.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.persona.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.representations.idm.PersonaJuridicaRepresentation;
import org.softgreen.sistcoop.persona.representations.idm.PersonaNaturalRepresentation;
import org.softgreen.sistcoop.persona.representations.idm.TipoDocumentoRepresentation;

public class RepresentationToModel {

    private static Logger logger = Logger.getLogger(RepresentationToModel.class);

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static TipoDocumentoModel createTipoDocumento(TipoDocumentoRepresentation rep, TipoDocumentoProvider provider) {
		String abreviatura = rep.getAbreviatura();
		String denominacion = rep.getDenominacion();
		int maxLength = rep.getCantidadCaracteres();
		TipoPersona tipoPersona = TipoPersona.valueOf(rep.getTipoPersona().toUpperCase());

		TipoDocumentoModel model = provider.addTipoDocumento(abreviatura, denominacion, maxLength, tipoPersona);
		return model;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static PersonaNaturalModel createPersonaNatural(PersonaNaturalRepresentation rep, PersonaNaturalProvider personaNaturalProvider, TipoDocumentoProvider tipoDocumentoProvider) {		
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(rep.getTipoDocumento());
		String numeroDocumento = rep.getNumeroDocumento();
		String paterno = rep.getApellidoPaterno();
		String materno = rep.getApellidoMaterno();
		String nombres = rep.getNombres();
		Sexo sexo = Sexo.lookup(rep.getSexo());
		Date fechaNacimiento = rep.getFechaNacimiento();
		
		EstadoCivil estadoCivil = EstadoCivil.lookup(rep.getEstadoCivil());
		String ocupacion = rep.getOcupacion();
		String urlFoto = rep.getUrlFoto();
		String urlFirma = rep.getUrlFirma();
		String ubigeo = rep.getUbigeo();
		String direccion = rep.getDireccion();
		String referencia = rep.getReferencia();
		String telefono = rep.getTelefono();
		String celular = rep.getCelular();
		String email = rep.getEmail();
		
		PersonaNaturalModel model = personaNaturalProvider.addPersonaNatural(tipoDocumentoModel, numeroDocumento, paterno, materno, nombres, sexo, fechaNacimiento);
		model.setEstadoCivil(estadoCivil);
		model.setOcupacion(ocupacion);
		model.setUrlFoto(urlFoto);
		model.setUrlFirma(urlFirma);
		model.setUbigeo(ubigeo);
		model.setDireccion(direccion);
		model.setReferencia(referencia);
		model.setTelefono(telefono);
		model.setCelular(celular);
		model.setEmail(email);		
		
		return model;
	}

	public static PersonaJuridicaModel createPersonaJuridica(PersonaJuridicaRepresentation rep, PersonaJuridicaProvider personaJuridicaProvider, TipoDocumentoProvider tipoDocumentoProvider) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(rep.getTipoDocumento());
		String numeroDocumento = rep.getNumeroDocumento();
		
		String razonSocial = rep.getRazonSocial();
		
		String ubigeo = rep.getUbigeo();
		String direccion = rep.getDireccion();
		String referencia = rep.getReferencia();
		String telefono = rep.getTelefono();
		String celular = rep.getCelular();
		String email = rep.getEmail();
		
		PersonaJuridicaModel model = personaJuridicaProvider.addPersonaJuridica(tipoDocumentoModel, numeroDocumento, razonSocial);
		
		
		model.setUbigeo(ubigeo);
		model.setDireccion(direccion);
		model.setReferencia(referencia);
		model.setTelefono(telefono);
		model.setCelular(celular);
		model.setEmail(email);
		
		model.commit();
		
		return model;
	}
	
    public static void importRealm(KeycloakSession session, RealmRepresentation rep, RealmModel newRealm) {
        newRealm.setName(rep.getRealm());
        if (rep.isEnabled() != null) newRealm.setEnabled(rep.isEnabled());
        if (rep.isSocial() != null) newRealm.setSocial(rep.isSocial());
        if (rep.isBruteForceProtected() != null) newRealm.setBruteForceProtected(rep.isBruteForceProtected());
        if (rep.getMaxFailureWaitSeconds() != null) newRealm.setMaxFailureWaitSeconds(rep.getMaxFailureWaitSeconds());
        if (rep.getMinimumQuickLoginWaitSeconds() != null) newRealm.setMinimumQuickLoginWaitSeconds(rep.getMinimumQuickLoginWaitSeconds());
        if (rep.getWaitIncrementSeconds() != null) newRealm.setWaitIncrementSeconds(rep.getWaitIncrementSeconds());
        if (rep.getQuickLoginCheckMilliSeconds() != null) newRealm.setQuickLoginCheckMilliSeconds(rep.getQuickLoginCheckMilliSeconds());
        if (rep.getMaxDeltaTimeSeconds() != null) newRealm.setMaxDeltaTimeSeconds(rep.getMaxDeltaTimeSeconds());
        if (rep.getFailureFactor() != null) newRealm.setFailureFactor(rep.getFailureFactor());

        if (rep.getNotBefore() != null) newRealm.setNotBefore(rep.getNotBefore());

        if (rep.getAccessTokenLifespan() != null) newRealm.setAccessTokenLifespan(rep.getAccessTokenLifespan());
        else newRealm.setAccessTokenLifespan(300);

        if (rep.getSsoSessionIdleTimeout() != null) newRealm.setSsoSessionIdleTimeout(rep.getSsoSessionIdleTimeout());
        else newRealm.setSsoSessionIdleTimeout(600);
        if (rep.getSsoSessionMaxLifespan() != null) newRealm.setSsoSessionMaxLifespan(rep.getSsoSessionMaxLifespan());
        else newRealm.setSsoSessionMaxLifespan(36000);

        if (rep.getAccessCodeLifespan() != null) newRealm.setAccessCodeLifespan(rep.getAccessCodeLifespan());
        else newRealm.setAccessCodeLifespan(60);

        if (rep.getAccessCodeLifespanUserAction() != null)
            newRealm.setAccessCodeLifespanUserAction(rep.getAccessCodeLifespanUserAction());
        else newRealm.setAccessCodeLifespanUserAction(300);

        if (rep.getSslRequired() != null) newRealm.setSslRequired(SslRequired.valueOf(rep.getSslRequired().toUpperCase()));
        if (rep.isPasswordCredentialGrantAllowed() != null) newRealm.setPasswordCredentialGrantAllowed(rep.isPasswordCredentialGrantAllowed());
        if (rep.isRegistrationAllowed() != null) newRealm.setRegistrationAllowed(rep.isRegistrationAllowed());
        if (rep.isRememberMe() != null) newRealm.setRememberMe(rep.isRememberMe());
        if (rep.isVerifyEmail() != null) newRealm.setVerifyEmail(rep.isVerifyEmail());
        if (rep.isResetPasswordAllowed() != null) newRealm.setResetPasswordAllowed(rep.isResetPasswordAllowed());
        if (rep.isUpdateProfileOnInitialSocialLogin() != null)
            newRealm.setUpdateProfileOnInitialSocialLogin(rep.isUpdateProfileOnInitialSocialLogin());
        if (rep.getPrivateKey() == null || rep.getPublicKey() == null) {
            KeycloakModelUtils.generateRealmKeys(newRealm);
        } else {
            newRealm.setPrivateKeyPem(rep.getPrivateKey());
            newRealm.setPublicKeyPem(rep.getPublicKey());
        }
        if (rep.getLoginTheme() != null) newRealm.setLoginTheme(rep.getLoginTheme());
        if (rep.getAccountTheme() != null) newRealm.setAccountTheme(rep.getAccountTheme());
        if (rep.getAdminTheme() != null) newRealm.setAdminTheme(rep.getAdminTheme());
        if (rep.getEmailTheme() != null) newRealm.setEmailTheme(rep.getEmailTheme());

        if (rep.getRequiredCredentials() != null) {
            for (String requiredCred : rep.getRequiredCredentials()) {
                addRequiredCredential(newRealm, requiredCred);
            }
        } else {
            addRequiredCredential(newRealm, CredentialRepresentation.PASSWORD);
        }

        if (rep.getPasswordPolicy() != null) newRealm.setPasswordPolicy(new PasswordPolicy(rep.getPasswordPolicy()));

        if (rep.getApplications() != null) {
            Map<String, ApplicationModel> appMap = createApplications(rep, newRealm);
        }

        if (rep.getRoles() != null) {
            if (rep.getRoles().getRealm() != null) { // realm roles
                for (RoleRepresentation roleRep : rep.getRoles().getRealm()) {
                    createRole(newRealm, roleRep);
                }
            }
            if (rep.getRoles().getApplication() != null) {
                for (Map.Entry<String, List<RoleRepresentation>> entry : rep.getRoles().getApplication().entrySet()) {
                    ApplicationModel app = newRealm.getApplicationByName(entry.getKey());
                    if (app == null) {
                        throw new RuntimeException("App doesn't exist in role definitions: " + entry.getKey());
                    }
                    for (RoleRepresentation roleRep : entry.getValue()) {
                        // Application role may already exists (for example if it is defaultRole)
                        RoleModel role = roleRep.getId()!=null ? app.addRole(roleRep.getId(), roleRep.getName()) : app.addRole(roleRep.getName());
                        role.setDescription(roleRep.getDescription());
                    }
                }
            }
            // now that all roles are created, re-iterate and set up composites
            if (rep.getRoles().getRealm() != null) { // realm roles
                for (RoleRepresentation roleRep : rep.getRoles().getRealm()) {
                    RoleModel role = newRealm.getRole(roleRep.getName());
                    addComposites(role, roleRep, newRealm);
                }
            }
            if (rep.getRoles().getApplication() != null) {
                for (Map.Entry<String, List<RoleRepresentation>> entry : rep.getRoles().getApplication().entrySet()) {
                    ApplicationModel app = newRealm.getApplicationByName(entry.getKey());
                    if (app == null) {
                        throw new RuntimeException("App doesn't exist in role definitions: " + entry.getKey());
                    }
                    for (RoleRepresentation roleRep : entry.getValue()) {
                        RoleModel role = app.getRole(roleRep.getName());
                        addComposites(role, roleRep, newRealm);
                    }
                }
            }
        }

        // Setup realm default roles
        if (rep.getDefaultRoles() != null) {
            for (String roleString : rep.getDefaultRoles()) {
                newRealm.addDefaultRole(roleString.trim());
            }
        }
        // Setup application default roles
        if (rep.getApplications() != null) {
            for (ApplicationRepresentation resourceRep : rep.getApplications()) {
                if (resourceRep.getDefaultRoles() != null) {
                    ApplicationModel appModel = newRealm.getApplicationByName(resourceRep.getName());
                    appModel.updateDefaultRoles(resourceRep.getDefaultRoles());
                }
            }
        }

        if (rep.getOauthClients() != null) {
            createOAuthClients(rep, newRealm);
        }


        // Now that all possible roles and applications are created, create scope mappings

        Map<String, ApplicationModel> appMap = newRealm.getApplicationNameMap();

        if (rep.getApplicationScopeMappings() != null) {

            for (Map.Entry<String, List<ScopeMappingRepresentation>> entry : rep.getApplicationScopeMappings().entrySet()) {
                ApplicationModel app = appMap.get(entry.getKey());
                if (app == null) {
                    throw new RuntimeException("Unable to find application role mappings for app: " + entry.getKey());
                }
                createApplicationScopeMappings(newRealm, app, entry.getValue());
            }
        }

        if (rep.getScopeMappings() != null) {
            for (ScopeMappingRepresentation scope : rep.getScopeMappings()) {
                ClientModel client = newRealm.findClient(scope.getClient());
                if (client == null) {
                    throw new RuntimeException("Unknown client specification in realm scope mappings");
                }
                for (String roleString : scope.getRoles()) {
                    RoleModel role = newRealm.getRole(roleString.trim());
                    if (role == null) {
                        role = newRealm.addRole(roleString.trim());
                    }
                    client.addScopeMapping(role);
                }

            }
        }

        if (rep.getSmtpServer() != null) {
            newRealm.setSmtpConfig(new HashMap(rep.getSmtpServer()));
        }

        if (rep.getBrowserSecurityHeaders() != null) {
            newRealm.setBrowserSecurityHeaders(rep.getBrowserSecurityHeaders());
        } else {
            newRealm.setBrowserSecurityHeaders(BrowserSecurityHeaders.defaultHeaders);
        }

        if (rep.getSocialProviders() != null) {
            newRealm.setSocialConfig(new HashMap(rep.getSocialProviders()));
        }
        if (rep.getUserFederationProviders() != null) {
            List<UserFederationProviderModel> providerModels = convertFederationProviders(rep.getUserFederationProviders());
            newRealm.setUserFederationProviders(providerModels);
        }

        // create users and their role mappings and social mappings

        if (rep.getUsers() != null) {
            for (UserRepresentation userRep : rep.getUsers()) {
                UserModel user = createUser(session, newRealm, userRep, appMap);
            }
        }
    }

    public static void updateRealm(RealmRepresentation rep, RealmModel realm) {
        if (rep.getRealm() != null) {
            realm.setName(rep.getRealm());
        }
        if (rep.isEnabled() != null) realm.setEnabled(rep.isEnabled());
        if (rep.isSocial() != null) realm.setSocial(rep.isSocial());
        if (rep.isBruteForceProtected() != null) realm.setBruteForceProtected(rep.isBruteForceProtected());
        if (rep.getMaxFailureWaitSeconds() != null) realm.setMaxFailureWaitSeconds(rep.getMaxFailureWaitSeconds());
        if (rep.getMinimumQuickLoginWaitSeconds() != null) realm.setMinimumQuickLoginWaitSeconds(rep.getMinimumQuickLoginWaitSeconds());
        if (rep.getWaitIncrementSeconds() != null) realm.setWaitIncrementSeconds(rep.getWaitIncrementSeconds());
        if (rep.getQuickLoginCheckMilliSeconds() != null) realm.setQuickLoginCheckMilliSeconds(rep.getQuickLoginCheckMilliSeconds());
        if (rep.getMaxDeltaTimeSeconds() != null) realm.setMaxDeltaTimeSeconds(rep.getMaxDeltaTimeSeconds());
        if (rep.getFailureFactor() != null) realm.setFailureFactor(rep.getFailureFactor());
        if (rep.isPasswordCredentialGrantAllowed() != null) realm.setPasswordCredentialGrantAllowed(rep.isPasswordCredentialGrantAllowed());
        if (rep.isRegistrationAllowed() != null) realm.setRegistrationAllowed(rep.isRegistrationAllowed());
        if (rep.isRememberMe() != null) realm.setRememberMe(rep.isRememberMe());
        if (rep.isVerifyEmail() != null) realm.setVerifyEmail(rep.isVerifyEmail());
        if (rep.isResetPasswordAllowed() != null) realm.setResetPasswordAllowed(rep.isResetPasswordAllowed());
        if (rep.isUpdateProfileOnInitialSocialLogin() != null)
            realm.setUpdateProfileOnInitialSocialLogin(rep.isUpdateProfileOnInitialSocialLogin());
        if (rep.getSslRequired() != null) realm.setSslRequired(SslRequired.valueOf(rep.getSslRequired().toUpperCase()));
        if (rep.getAccessCodeLifespan() != null) realm.setAccessCodeLifespan(rep.getAccessCodeLifespan());
        if (rep.getAccessCodeLifespanUserAction() != null)
            realm.setAccessCodeLifespanUserAction(rep.getAccessCodeLifespanUserAction());
        if (rep.getNotBefore() != null) realm.setNotBefore(rep.getNotBefore());
        if (rep.getAccessTokenLifespan() != null) realm.setAccessTokenLifespan(rep.getAccessTokenLifespan());
        if (rep.getSsoSessionIdleTimeout() != null) realm.setSsoSessionIdleTimeout(rep.getSsoSessionIdleTimeout());
        if (rep.getSsoSessionMaxLifespan() != null) realm.setSsoSessionMaxLifespan(rep.getSsoSessionMaxLifespan());
        if (rep.getRequiredCredentials() != null) {
            realm.updateRequiredCredentials(rep.getRequiredCredentials());
        }
        if (rep.getLoginTheme() != null) realm.setLoginTheme(rep.getLoginTheme());
        if (rep.getAccountTheme() != null) realm.setAccountTheme(rep.getAccountTheme());
        if (rep.getAdminTheme() != null) realm.setAdminTheme(rep.getAdminTheme());
        if (rep.getEmailTheme() != null) realm.setEmailTheme(rep.getEmailTheme());

        if (rep.getPasswordPolicy() != null) realm.setPasswordPolicy(new PasswordPolicy(rep.getPasswordPolicy()));

        if (rep.getDefaultRoles() != null) {
            realm.updateDefaultRoles(rep.getDefaultRoles().toArray(new String[rep.getDefaultRoles().size()]));
        }

        if (rep.getSmtpServer() != null) {
            realm.setSmtpConfig(new HashMap(rep.getSmtpServer()));
        }

        if (rep.getSocialProviders() != null) {
            realm.setSocialConfig(new HashMap(rep.getSocialProviders()));
        }

        if (rep.getBrowserSecurityHeaders() != null) {
            realm.setBrowserSecurityHeaders(rep.getBrowserSecurityHeaders());
        }

        if (rep.getUserFederationProviders() != null) {
            List<UserFederationProviderModel> providerModels = convertFederationProviders(rep.getUserFederationProviders());
            realm.setUserFederationProviders(providerModels);
        }

        if ("GENERATE".equals(rep.getPublicKey())) {
            KeycloakModelUtils.generateRealmKeys(realm);
        }
    }

    // Basic realm stuff

    public static void addRequiredCredential(RealmModel newRealm, String requiredCred) {
        newRealm.addRequiredCredential(requiredCred);
    }


    private static List<UserFederationProviderModel> convertFederationProviders(List<UserFederationProviderRepresentation> providers) {
        List<UserFederationProviderModel> result = new ArrayList<UserFederationProviderModel>();

        for (UserFederationProviderRepresentation representation : providers) {
            UserFederationProviderModel model = new UserFederationProviderModel(representation.getId(), representation.getProviderName(),
                    representation.getConfig(), representation.getPriority(), representation.getDisplayName(),
                    representation.getFullSyncPeriod(), representation.getChangedSyncPeriod(), representation.getLastSync());
            result.add(model);
        }
        return result;
    }

    // Roles

    public static void createRole(RealmModel newRealm, RoleRepresentation roleRep) {
        RoleModel role = roleRep.getId()!=null ? newRealm.addRole(roleRep.getId(), roleRep.getName()) : newRealm.addRole(roleRep.getName());
        if (roleRep.getDescription() != null) role.setDescription(roleRep.getDescription());
    }

    private static void addComposites(RoleModel role, RoleRepresentation roleRep, RealmModel realm) {
        if (roleRep.getComposites() == null) return;
        if (roleRep.getComposites().getRealm() != null) {
            for (String roleStr : roleRep.getComposites().getRealm()) {
                RoleModel realmRole = realm.getRole(roleStr);
                if (realmRole == null) throw new RuntimeException("Unable to find composite realm role: " + roleStr);
                role.addCompositeRole(realmRole);
            }
        }
        if (roleRep.getComposites().getApplication() != null) {
            for (Map.Entry<String, List<String>> entry : roleRep.getComposites().getApplication().entrySet()) {
                ApplicationModel app = realm.getApplicationByName(entry.getKey());
                if (app == null) {
                    throw new RuntimeException("App doesn't exist in role definitions: " + roleRep.getName());
                }
                for (String roleStr : entry.getValue()) {
                    RoleModel appRole = app.getRole(roleStr);
                    if (appRole == null) throw new RuntimeException("Unable to find composite app role: " + roleStr);
                    role.addCompositeRole(appRole);
                }

            }

        }

    }

    // APPLICATIONS

    private static Map<String, ApplicationModel> createApplications(RealmRepresentation rep, RealmModel realm) {
        Map<String, ApplicationModel> appMap = new HashMap<String, ApplicationModel>();
        for (ApplicationRepresentation resourceRep : rep.getApplications()) {
            ApplicationModel app = createApplication(realm, resourceRep, false);
            appMap.put(app.getName(), app);
        }
        return appMap;
    }

    /**
     * Does not create scope or role mappings!
     *
     * @param realm
     * @param resourceRep
     * @return
     */
    public static ApplicationModel createApplication(RealmModel realm, ApplicationRepresentation resourceRep, boolean addDefaultRoles) {
        logger.debug("************ CREATE APPLICATION: {0}" + resourceRep.getName());
        ApplicationModel applicationModel = resourceRep.getId()!=null ? realm.addApplication(resourceRep.getId(), resourceRep.getName()) : realm.addApplication(resourceRep.getName());
        if (resourceRep.isEnabled() != null) applicationModel.setEnabled(resourceRep.isEnabled());
        applicationModel.setManagementUrl(resourceRep.getAdminUrl());
        if (resourceRep.isSurrogateAuthRequired() != null)
            applicationModel.setSurrogateAuthRequired(resourceRep.isSurrogateAuthRequired());
        applicationModel.setBaseUrl(resourceRep.getBaseUrl());
        if (resourceRep.isBearerOnly() != null) applicationModel.setBearerOnly(resourceRep.isBearerOnly());
        if (resourceRep.isPublicClient() != null) applicationModel.setPublicClient(resourceRep.isPublicClient());
        if (resourceRep.isFullScopeAllowed() != null) applicationModel.setFullScopeAllowed(resourceRep.isFullScopeAllowed());
        else applicationModel.setFullScopeAllowed(true);
        applicationModel.updateApplication();

        if (resourceRep.getNotBefore() != null) {
            applicationModel.setNotBefore(resourceRep.getNotBefore());
        }

        applicationModel.setSecret(resourceRep.getSecret());
        if (applicationModel.getSecret() == null) {
            KeycloakModelUtils.generateSecret(applicationModel);
        }


        if (resourceRep.getRedirectUris() != null) {
            for (String redirectUri : resourceRep.getRedirectUris()) {
                applicationModel.addRedirectUri(redirectUri);
            }
        }
        if (resourceRep.getWebOrigins() != null) {
            for (String webOrigin : resourceRep.getWebOrigins()) {
                logger.debugv("Application: {0} webOrigin: {1}", resourceRep.getName(), webOrigin);
                applicationModel.addWebOrigin(webOrigin);
            }
        } else {
            // add origins from redirect uris
            if (resourceRep.getRedirectUris() != null) {
                Set<String> origins = new HashSet<String>();
                for (String redirectUri : resourceRep.getRedirectUris()) {
                    logger.debugv("add redirect-uri to origin: {0}", redirectUri);
                    if (redirectUri.startsWith("http:")) {
                        URI uri = URI.create(redirectUri);
                        String origin = uri.getScheme() + "://" + uri.getHost();
                        if (uri.getPort() != -1) {
                            origin += ":" + uri.getPort();
                        }
                        logger.debugv("adding default application origin: {0}" , origin);
                        origins.add(origin);
                    }
                }
                if (origins.size() > 0) {
                    applicationModel.setWebOrigins(origins);
                }
            }
        }

        if (addDefaultRoles && resourceRep.getDefaultRoles() != null) {
            applicationModel.updateDefaultRoles(resourceRep.getDefaultRoles());
        }

        if (resourceRep.getClaims() != null) {
            setClaims(applicationModel, resourceRep.getClaims());
        } else {
            applicationModel.setAllowedClaimsMask(ClaimMask.ALL);
        }

        return applicationModel;
    }

    public static void updateApplication(ApplicationRepresentation rep, ApplicationModel resource) {
        if (rep.getName() != null) resource.setName(rep.getName());
        if (rep.isEnabled() != null) resource.setEnabled(rep.isEnabled());
        if (rep.isBearerOnly() != null) resource.setBearerOnly(rep.isBearerOnly());
        if (rep.isPublicClient() != null) resource.setPublicClient(rep.isPublicClient());
        if (rep.isFullScopeAllowed() != null) resource.setFullScopeAllowed(rep.isFullScopeAllowed());
        if (rep.getAdminUrl() != null) resource.setManagementUrl(rep.getAdminUrl());
        if (rep.getBaseUrl() != null) resource.setBaseUrl(rep.getBaseUrl());
        if (rep.isSurrogateAuthRequired() != null) resource.setSurrogateAuthRequired(rep.isSurrogateAuthRequired());
        resource.updateApplication();

        if (rep.getNotBefore() != null) {
            resource.setNotBefore(rep.getNotBefore());
        }
        if (rep.getDefaultRoles() != null) {
            resource.updateDefaultRoles(rep.getDefaultRoles());
        }

        List<String> redirectUris = rep.getRedirectUris();
        if (redirectUris != null) {
            resource.setRedirectUris(new HashSet<String>(redirectUris));
        }

        List<String> webOrigins = rep.getWebOrigins();
        if (webOrigins != null) {
            resource.setWebOrigins(new HashSet<String>(webOrigins));
        }

        if (rep.getClaims() != null) {
            setClaims(resource, rep.getClaims());
        }
    }

    public static void setClaims(ClientModel model, ClaimRepresentation rep) {
        long mask = model.getAllowedClaimsMask();
        if (rep.getAddress()) {
            mask |= ClaimMask.ADDRESS;
        } else {
            mask &= ~ClaimMask.ADDRESS;
        }
        if (rep.getEmail()) {
            mask |= ClaimMask.EMAIL;
        } else {
            mask &= ~ClaimMask.EMAIL;
        }
        if (rep.getGender()) {
            mask |= ClaimMask.GENDER;
        } else {
            mask &= ~ClaimMask.GENDER;
        }
        if (rep.getLocale()) {
            mask |= ClaimMask.LOCALE;
        } else {
            mask &= ~ClaimMask.LOCALE;
        }
        if (rep.getName()) {
            mask |= ClaimMask.NAME;
        } else {
            mask &= ~ClaimMask.NAME;
        }
        if (rep.getPhone()) {
            mask |= ClaimMask.PHONE;
        } else {
            mask &= ~ClaimMask.PHONE;
        }
        if (rep.getPicture()) {
            mask |= ClaimMask.PICTURE;
        } else {
            mask &= ~ClaimMask.PICTURE;
        }
        if (rep.getProfile()) {
            mask |= ClaimMask.PROFILE;
        } else {
            mask &= ~ClaimMask.PROFILE;
        }
        if (rep.getUsername()) {
            mask |= ClaimMask.USERNAME;
        } else {
            mask &= ~ClaimMask.USERNAME;
        }
        if (rep.getWebsite()) {
            mask |= ClaimMask.WEBSITE;
        } else {
            mask &= ~ClaimMask.WEBSITE;
        }
        model.setAllowedClaimsMask(mask);
    }

    // OAuth clients

    private static void createOAuthClients(RealmRepresentation realmRep, RealmModel realm) {
        for (OAuthClientRepresentation rep : realmRep.getOauthClients()) {
            createOAuthClient(rep, realm);
        }
    }

    public static OAuthClientModel createOAuthClient(String id, String name, RealmModel realm) {
        OAuthClientModel model = id!=null ? realm.addOAuthClient(id, name) : realm.addOAuthClient(name);
        KeycloakModelUtils.generateSecret(model);
        return model;
    }

    public static OAuthClientModel createOAuthClient(OAuthClientRepresentation rep, RealmModel realm) {
        OAuthClientModel model = createOAuthClient(rep.getId(), rep.getName(), realm);
        updateOAuthClient(rep, model);
        return model;
    }

    public static void updateOAuthClient(OAuthClientRepresentation rep, OAuthClientModel model) {
        if (rep.getName() != null) model.setClientId(rep.getName());
        if (rep.isEnabled() != null) model.setEnabled(rep.isEnabled());
        if (rep.isPublicClient() != null) model.setPublicClient(rep.isPublicClient());
        if (rep.isFullScopeAllowed() != null) model.setFullScopeAllowed(rep.isFullScopeAllowed());
        if (rep.isDirectGrantsOnly() != null) model.setDirectGrantsOnly(rep.isDirectGrantsOnly());
        if (rep.getClaims() != null) {
            setClaims(model, rep.getClaims());
        }
        if (rep.getNotBefore() != null) {
            model.setNotBefore(rep.getNotBefore());
        }
        if (rep.getSecret() != null) model.setSecret(rep.getSecret());
        List<String> redirectUris = rep.getRedirectUris();
        if (redirectUris != null) {
            model.setRedirectUris(new HashSet<String>(redirectUris));
        }

        List<String> webOrigins = rep.getWebOrigins();
        if (webOrigins != null) {
            model.setWebOrigins(new HashSet<String>(webOrigins));
        }

        if (rep.getClaims() != null) {
            setClaims(model, rep.getClaims());
        }

        if (rep.getNotBefore() != null) {
            model.setNotBefore(rep.getNotBefore());
        }

    }

    // Scope mappings

    public static void createApplicationScopeMappings(RealmModel realm, ApplicationModel applicationModel, List<ScopeMappingRepresentation> mappings) {
        for (ScopeMappingRepresentation mapping : mappings) {
            ClientModel client = realm.findClient(mapping.getClient());
            if (client == null) {
                throw new RuntimeException("Unknown client specified in application scope mappings");
            }
            for (String roleString : mapping.getRoles()) {
                RoleModel role = applicationModel.getRole(roleString.trim());
                if (role == null) {
                    role = applicationModel.addRole(roleString.trim());
                }
                client.addScopeMapping(role);
            }
        }
    }

    // Users

    public static UserModel createUser(KeycloakSession session, RealmModel newRealm, UserRepresentation userRep, Map<String, ApplicationModel> appMap) {
        // Import users just to user storage. Don't federate
        UserModel user = session.userStorage().addUser(newRealm, userRep.getId(), userRep.getUsername(), false);
        user.setEnabled(userRep.isEnabled());
        user.setEmail(userRep.getEmail());
        user.setFirstName(userRep.getFirstName());
        user.setLastName(userRep.getLastName());
        user.setFederationLink(userRep.getFederationLink());
        if (userRep.getAttributes() != null) {
            for (Map.Entry<String, String> entry : userRep.getAttributes().entrySet()) {
                user.setAttribute(entry.getKey(), entry.getValue());
            }
        }
        if (userRep.getRequiredActions() != null) {
            for (String requiredAction : userRep.getRequiredActions()) {
                user.addRequiredAction(UserModel.RequiredAction.valueOf(requiredAction));
            }
        }
        if (userRep.getCredentials() != null) {
            for (CredentialRepresentation cred : userRep.getCredentials()) {
                updateCredential(user, cred);
            }
        }
        if (userRep.getSocialLinks() != null) {
            for (SocialLinkRepresentation socialLink : userRep.getSocialLinks()) {
                SocialLinkModel mappingModel = new SocialLinkModel(socialLink.getSocialProvider(), socialLink.getSocialUserId(), socialLink.getSocialUsername());
                session.users().addSocialLink(newRealm, user, mappingModel);
            }
        }
        if (userRep.getRealmRoles() != null) {
            for (String roleString : userRep.getRealmRoles()) {
                RoleModel role = newRealm.getRole(roleString.trim());
                if (role == null) {
                    role = newRealm.addRole(roleString.trim());
                }
                user.grantRole(role);
            }
        }
        if (userRep.getApplicationRoles() != null) {
            for (Map.Entry<String, List<String>> entry : userRep.getApplicationRoles().entrySet()) {
                ApplicationModel app = appMap.get(entry.getKey());
                if (app == null) {
                    throw new RuntimeException("Unable to find application role mappings for app: " + entry.getKey());
                }
                createApplicationRoleMappings(app, user, entry.getValue());
            }
        }
        return user;
    }

    // Detect if it is "plain-text" or "hashed" representation and update model according to it
    private static void updateCredential(UserModel user, CredentialRepresentation cred) {
        if (cred.getValue() != null) {
            UserCredentialModel plainTextCred = convertCredential(cred);
            user.updateCredential(plainTextCred);
        } else {
            UserCredentialValueModel hashedCred = new UserCredentialValueModel();
            hashedCred.setType(cred.getType());
            hashedCred.setDevice(cred.getDevice());
            hashedCred.setHashIterations(cred.getHashIterations());
            try {
                hashedCred.setSalt(Base64.decode(cred.getSalt()));
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
            hashedCred.setValue(cred.getHashedSaltedValue());
            user.updateCredentialDirectly(hashedCred);
        }
    }

    public static UserCredentialModel convertCredential(CredentialRepresentation cred) {
        UserCredentialModel credential = new UserCredentialModel();
        credential.setType(cred.getType());
        credential.setValue(cred.getValue());
        return credential;
    }

    // Role mappings

    public static void createApplicationRoleMappings(ApplicationModel applicationModel, UserModel user, List<String> roleNames) {
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        for (String roleName : roleNames) {
            RoleModel role = applicationModel.getRole(roleName.trim());
            if (role == null) {
                role = applicationModel.addRole(roleName.trim());
            }
            user.grantRole(role);

        }
    }

}

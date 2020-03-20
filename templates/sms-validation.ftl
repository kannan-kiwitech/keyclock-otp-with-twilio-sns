<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "title" || section = "header">
        ${msg("sms-auth.title")}
    <#elseif section = "form">
        <#--
            Hack-alert: Keycloak doesn't provide per-field error messages here,
            so we check global message for need to display validation error styling
        -->
        <#if message?has_content && message.type = "error">
            <#assign errorClass = "govuk-form-group--error" >
        </#if>
        <div class="govuk-grid-row">
            <div class="govuk-grid-column-full"><p>${msg("sms-auth.instruction")}</p></div>
            <form id="kc-totp-login-form" class="${properties.kcFormClass!} govuk-grid-column-two-thirds" action="${url.loginAction}" method="post">
                <div class="govuk-form-group form-group  ${errorClass!""} ">
                    <label for="totp" class="control-label">${msg("sms-auth.code")}</label>
                    <input id="totp" name="smsCode" type="text" class="govuk-input form-control" autocomplete="false"/>
                </div>
                <input class="govuk-button btn btn-primary btn-block btn-lg" name="login" id="kc-login" type="submit" value="${msg("doSubmit")}"/>
                <input class="govuk-button btn btn-primary btn-block btn-lg" style="margin: 10px 0 20px 0;" name="login" id="kc-resend" type="submit" value="${msg("sms-auth.resend")}"/>
            </form>
        </div>
        <#if client?? && client.baseUrl?has_content>
            <p style="margin-bottom: 0;"><a id="backToApplication" href="${client.baseUrl}">${msg("backToApplication")}</a></p>
        </#if>
    </#if>
    <div id="snackbar">${msg("sms-auth.instruction")}</div>
</@layout.registrationLayout>

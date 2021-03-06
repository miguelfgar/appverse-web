/*
 Copyright (c) 2012 GFT Appverse, S.L., Sociedad Unipersonal.

 This Source Code Form is subject to the terms of the Appverse Public License 
 Version 2.0 (“APL v2.0”). If a copy of the APL was not distributed with this 
 file, You can obtain one at http://www.appverse.mobi/licenses/apl_v2.0.pdf. [^]

 Redistribution and use in source and binary forms, with or without modification, 
 are permitted provided that the conditions of the AppVerse Public License v2.0 
 are met.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. EXCEPT IN CASE OF WILLFUL MISCONDUCT OR GROSS NEGLIGENCE, IN NO EVENT
 SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) 
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 POSSIBILITY OF SUCH DAMAGE.
 */
package org.appverse.web.showcases.gwtshowcase.gwtfrontend.admin.common.layout.views.impl.gxt;

import org.appverse.web.framework.frontend.gwt.rmvp.ReverseComposite;
import org.appverse.web.showcases.gwtshowcase.gwtfrontend.admin.common.layout.presenters.interfaces.AdminHeaderView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

@Singleton
public class AdminHeaderViewImpl extends
		ReverseComposite<AdminHeaderView.IAdminHeaderPresenter> implements
		AdminHeaderView {

	interface AdminHeaderUiBinder extends UiBinder<Widget, AdminHeaderViewImpl> {
	}

	private static AdminHeaderUiBinder uiBinder = GWT
			.create(AdminHeaderUiBinder.class);

	@UiField
	Label logoutLink, homeLink;
		
	@UiField
	BorderLayoutContainer headerBorderContainer;

	private void centerBorderLayout() {
		int width = Window.getClientWidth();
		int desiredWidth = 1024;
		int leftMargin = (width - desiredWidth) / 2;
		if (leftMargin < 0)
			leftMargin = 0;
		headerBorderContainer.setPosition(leftMargin, 0);
	}	

	@Override
	public void createView() {
		initWidget(uiBinder.createAndBindUi(this));
		centerBorderLayout();
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(final ResizeEvent event) {
				centerBorderLayout();
			}
		});		
	}
	
	@UiHandler("homeLink")
	public void onHomeLinkClick(final ClickEvent event) {
		presenter.homeClicked();
	}	

	@UiHandler("logoutLink")
	public void onLogoutLinkClick(final ClickEvent event) {
		presenter.logoutClicked();
	}
}
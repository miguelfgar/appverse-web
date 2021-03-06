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
package org.appverse.web.framework.backend.api.converters;

import java.util.List;

import org.appverse.web.framework.backend.api.model.business.AbstractBusinessBean;
import org.appverse.web.framework.backend.api.model.presentation.AbstractPresentationBean;

public interface IP2BBeanConverter<PresentationBean extends AbstractPresentationBean, BusinessBean extends AbstractBusinessBean>
		extends IBeanConverter {

	PresentationBean convert(BusinessBean businessBean) throws Exception;

	PresentationBean convert(BusinessBean businessBean,
			ConversionType conversionType) throws Exception;

	void convert(BusinessBean businessBean, PresentationBean presentationBean)
			throws Exception;

	void convert(BusinessBean businessBean, PresentationBean presentationBean,
			ConversionType conversionType) throws Exception;

	BusinessBean convert(PresentationBean presentationBean) throws Exception;

	void convert(PresentationBean presentationBean, BusinessBean businessBean)
			throws Exception;

	void convert(PresentationBean presentationBean, BusinessBean businessBean,
			ConversionType conversionType) throws Exception;

	BusinessBean convert(PresentationBean presentationBean,
			ConversionType conversionType) throws Exception;

	List<PresentationBean> convertBusinessList(List<BusinessBean> bean)
			throws Exception;

	List<PresentationBean> convertBusinessList(
			List<BusinessBean> businessBeans, ConversionType conversionType)
			throws Exception;

	void convertBusinessList(List<BusinessBean> businessBean,
			List<PresentationBean> presentationBean) throws Exception;

	void convertBusinessList(List<BusinessBean> businessBean,
			List<PresentationBean> presentationBean,
			ConversionType conversionType) throws Exception;

	List<BusinessBean> convertPresentationList(
			List<PresentationBean> presentationBeans) throws Exception;

	List<BusinessBean> convertPresentationList(
			List<PresentationBean> presentationBeans,
			ConversionType conversionType) throws Exception;

	void convertPresentationList(List<PresentationBean> presentationBean,
			List<BusinessBean> businessBean) throws Exception;

	void convertPresentationList(List<PresentationBean> presentationBean,
			List<BusinessBean> businessBean, ConversionType conversionType)
			throws Exception;
}

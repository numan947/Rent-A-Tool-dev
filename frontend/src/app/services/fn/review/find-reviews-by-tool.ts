/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageResponseDtoReviewResponseDto } from '../../models/page-response-dto-review-response-dto';

export interface FindReviewsByTool$Params {
  'tool-id': number;
  page?: number;
  size?: number;
}

export function findReviewsByTool(http: HttpClient, rootUrl: string, params: FindReviewsByTool$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseDtoReviewResponseDto>> {
  const rb = new RequestBuilder(rootUrl, findReviewsByTool.PATH, 'get');
  if (params) {
    rb.path('tool-id', params['tool-id'], {});
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageResponseDtoReviewResponseDto>;
    })
  );
}

findReviewsByTool.PATH = '/reviews/tool/{tool-id}';

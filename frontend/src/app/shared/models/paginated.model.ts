import {PageInfo} from '@app/shared/models/page-info.model';

export interface PaginatedResponse<T> {
  content: T[];
  page: PageInfo
}

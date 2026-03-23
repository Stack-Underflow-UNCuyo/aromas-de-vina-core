import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IFile, NewFile } from '../file.model';

@Injectable()
export class FilesService {
  readonly filesParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(undefined);
  readonly filesResource = httpResource<IFile[]>(() => {
    const params = this.filesParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of file that have been fetched. It is updated when the filesResource emits a new value.
   * In case of error while fetching the files, the signal is set to an empty array.
   */
  readonly files = computed(() => (this.filesResource.hasValue() ? this.filesResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/files');
}

@Injectable({ providedIn: 'root' })
export class FileService extends FilesService {
  protected readonly http = inject(HttpClient);

  create(file: NewFile): Observable<IFile> {
    return this.http.post<IFile>(this.resourceUrl, file);
  }

  find(id: string): Observable<IFile> {
    return this.http.get<IFile>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  query(req?: any): Observable<HttpResponse<IFile[]>> {
    const options = createRequestOption(req);
    return this.http.get<IFile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getFileIdentifier(file: Pick<IFile, 'id'>): string {
    return file.id;
  }

  compareFile(o1: Pick<IFile, 'id'> | null, o2: Pick<IFile, 'id'> | null): boolean {
    return o1 && o2 ? this.getFileIdentifier(o1) === this.getFileIdentifier(o2) : o1 === o2;
  }

  addFileToCollectionIfMissing<Type extends Pick<IFile, 'id'>>(
    fileCollection: Type[],
    ...filesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const files: Type[] = filesToCheck.filter(isPresent);
    if (files.length > 0) {
      const fileCollectionIdentifiers = fileCollection.map(fileItem => this.getFileIdentifier(fileItem));
      const filesToAdd = files.filter(fileItem => {
        const fileIdentifier = this.getFileIdentifier(fileItem);
        if (fileCollectionIdentifiers.includes(fileIdentifier)) {
          return false;
        }
        fileCollectionIdentifiers.push(fileIdentifier);
        return true;
      });
      return [...filesToAdd, ...fileCollection];
    }
    return fileCollection;
  }
}

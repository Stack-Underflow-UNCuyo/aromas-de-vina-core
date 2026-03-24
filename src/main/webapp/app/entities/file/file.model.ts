import { FileVisibility } from 'app/entities/enumerations/file-visibility.model';

export interface IFile {
  id: string;
  visibility?: FileVisibility;
  url?: string | null;
  createdBy?: string | null;
  createdDate?: string | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: string | null;
}

export type NewFile = Omit<IFile, 'id'> & {
  id: null;
  visibility: FileVisibility;
};

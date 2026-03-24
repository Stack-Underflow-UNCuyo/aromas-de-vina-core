import { FileVisibility } from 'app/entities/enumerations/file-visibility.model';

export interface IFile {
  id: string;
  visibility: FileVisibility;
  url: string;
  createdBy: string;
  createdDate: string;
  lastModifiedBy: string;
  lastModifiedDate: string;
}

// Cleaner NewFile type
export type NewFile = Omit<IFile, 'id' | 'createdBy' | 'createdDate' | 'lastModifiedBy' | 'lastModifiedDate'> & {
  id: null;
};
